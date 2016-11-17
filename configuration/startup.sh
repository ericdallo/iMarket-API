#!/bin/bash
set -ve

APP=imarket-api

curl -s "https://storage.googleapis.com/signals-agents/logging/google-fluentd-install.sh" | bash
service google-fluentd restart &

GCSFUSE_REPO=gcsfuse-`lsb_release -c -s`
DOCKER_USER=$(curl http://metadata.google.internal/computeMetadata/v1/instance/attributes/docker_user -H "Metadata-Flavor: Google")
DOCKER_PASS=$(curl http://metadata.google.internal/computeMetadata/v1/instance/attributes/docker_pass -H "Metadata-Flavor: Google")
DEPLOY_SCRIPT=$(curl http://metadata.google.internal/computeMetadata/v1/instance/attributes/deploy_script -H "Metadata-Flavor: Google")

echo "deb http://packages.cloud.google.com/apt $GCSFUSE_REPO main" | sudo tee /etc/apt/sources.list.d/gcsfuse.list
curl https://packages.cloud.google.com/apt/doc/apt-key.gpg | sudo apt-key add -
echo "deb https://apt.dockerproject.org/repo ubuntu-xenial main" | sudo tee /etc/apt/sources.list.d/docker.list
apt-key adv --keyserver hkp://p80.pool.sks-keyservers.net:80 --recv-keys 58118E89F3A912897C070ADBF76221572C52609D

apt-get update
apt-get install -yq docker-engine gcsfuse

systemctl start docker
systemctl enable docker

docker login --username=$DOCKER_USER --password=$DOCKER_PASS
docker pull imarket/$APP

# Mount bucket
BUCKET_DIR=/opt/bucket

mkdir -p $BUCKET_DIR
gcsfuse configuration.imarketbr.com $BUCKET_DIR

cp -rfv $BUCKET_DIR/$APP/prod/nginx.conf /opt
cp -rfv $BUCKET_DIR/ssl/imarketbr.com.crt /opt
cp -rfv $BUCKET_DIR/ssl/imarketbr.com.key /opt

# Deploy script
echo "$DEPLOY_SCRIPT" > /opt/deploy.sh
chmod +x /opt/deploy.sh
bash /opt/deploy.sh

# Nginx container
NGINX=nginx
docker pull $NGINX
if docker ps | awk -v app="NGINX" 'NR>1{  ($(NF) == NGINX )  }'; then 
    docker stop "$NGINX" && docker rm -f "$NGINX" 
fi

docker run --name $NGINX -d \
    -v /opt/nginx.conf:/etc/nginx/nginx.conf \
    -v /opt/imarketbr.com.crt:/etc/nginx/ssl/imarketbr.com.crt \
    -v /opt/imarketbr.com.key:/etc/nginx/ssl/imarketbr.com.key \
    --link $APP:$APP -p 80:80 -p 443:443 $NGINX
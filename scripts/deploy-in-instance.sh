#!/bin/bash
APP=imarket-api
BUCKET_DIR=/tmp/bucket
IMARKET_API_PROPERTIES=/opt/application.properties

mkdir -p $BUCKET_DIR
gcsfuse configuration.imarketbr.com $BUCKET_DIR

cp $BUCKET_DIR/$APP/prod/production.properties $IMARKET_API_PROPERTIES
fusermount -u $BUCKET_DIR
rm -r $BUCKET_DIR

docker pull imarket/$APP:latest
if docker ps | awk -v app="APP" 'NR>1{  ($(NF) == APP )  }'; then
  docker stop "$APP" && docker rm -f "$APP"
fi
docker run --name $APP -d -v $IMARKET_API_PROPERTIES:$IMARKET_API_PROPERTIES -p 80:9090 imarket/$APP
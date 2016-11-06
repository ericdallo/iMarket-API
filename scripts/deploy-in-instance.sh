#!/bin/bash

BUCKET_DIR=/tmp/bucket
IMARKET_API_PROPERTIES=/opt/application.properties

mkdir -p $BUCKET_DIR
gcsfuse configuration.imarketbr.com $BUCKET_DIR

cp $BUCKET_DIR/imarket-api/prod/production.properties $IMARKET_API_PROPERTIES
fusermount -u $BUCKET_DIR
rm -r $BUCKET_DIR

docker pull imarket/imarket-api:latest
docker stop imarket-api
docker rm imarket-api
docker run --name imarket-api -d -v $IMARKET_API_PROPERTIES:$IMARKET_API_PROPERTIES -p 80:9090 imarket/imarket-api

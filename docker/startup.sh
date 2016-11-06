#!/bin/bash
set -ve

BUCKET_DIR=/tmp/bucket
IMARKET_API_PROPERTIES=/opt/application.properties

mkdir -p $BUCKET_DIR
gcsfuse configuration.imarketbr.com $BUCKET_DIR

cp $BUCKET_DIR/imarket-api/prod/production.properties $IMARKET_API_PROPERTIES
fusermount -u $BUCKET_DIR
rm -r $BUCKET_DIR

cd $IMARKET_API_HOME

SPRING_CONFIG="--spring.config.location=file://$IMARKET_API_PROPERTIES"

exec java -jar build/libs/iMarket-API.jar $SPRING_CONFIG

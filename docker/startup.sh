#!/bin/bash
set -ve

# Cloud SQL
SQL_DIR=/opt/cloudsql
SQL_INSTANCE=imarket-2016:us-east1:imarket-db
mkdir -p $SQL_DIR
wget https://dl.google.com/cloudsql/cloud_sql_proxy.linux.amd64 -O $SQL_DIR/cloud_sql_proxy
chmod +x $SQL_DIR/cloud_sql_proxy
$SQL_DIR/cloud_sql_proxy -instances=$SQL_INSTANCE=tcp:3306 &

git clone https://github.com/iMarketbr/iMarket-API.git $IMARKET_API_HOME
IMARKET_API_PROPERTIES=/opt/application.properties

cd $IMARKET_API_HOME
./gradlew build

SPRING_CONFIG="--spring.config.location=file://$IMARKET_API_PROPERTIES"
exec java -jar $IMARKET_API_HOME/build/libs/imarket-api.jar $SPRING_CONFIG

#!/bin/bash
set -ve

git clone https://github.com/iMarketbr/iMarket-API.git $IMARKET_API_HOME
IMARKET_API_PROPERTIES=/opt/application.properties

cd $IMARKET_API_HOME
./gradlew build

SPRING_CONFIG="--spring.config.location=file://$IMARKET_API_PROPERTIES"
exec java -jar $IMARKET_API_HOME/build/libs/app.jar $SPRING_CONFIG

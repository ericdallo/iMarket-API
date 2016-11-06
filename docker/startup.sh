#!/bin/bash
set -ve

IMARKET_API_PROPERTIES=/opt/application.properties

cd $IMARKET_API_HOME

SPRING_CONFIG="--spring.config.location=file://$IMARKET_API_PROPERTIES"

exec java -jar build/libs/iMarket-API.jar $SPRING_CONFIG

#!/bin/bash

APP_API=imarket-api
IMARKET_API_PROPERTIES=/opt/application.properties

cp -rfv /opt/bucket/$APP_API/prod/production.properties $IMARKET_API_PROPERTIES

if docker ps | awk -v app="APP_API" 'NR>1{  ($(NF) == APP_API )  }'; then 
    docker stop "$APP_API" && docker rm -f "$APP_API" 
fi

docker run --name $APP_API -d -p 9090:9090 -v $IMARKET_API_PROPERTIES:$IMARKET_API_PROPERTIES imarket/$APP_API
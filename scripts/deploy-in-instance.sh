#!/bin/bash

docker pull imarket/imarket-api:latest
docker stop imarket-api
docker rm imarket-api
docker run --name imarket-api -d -p 80:9090 -v /tmp/production.properties:/opt/app/app/src/js/env.js imarket/imarket-web

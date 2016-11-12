FROM openjdk:8

ENV IMARKET_API_HOME /opt/imarket-api

RUN mkdir -p /opt/app

COPY . /opt/app
WORKDIR $IMARKET_API_HOME

ENTRYPOINT /opt/app/docker/startup.sh

EXPOSE 9090
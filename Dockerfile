FROM openjdk:8

ENV IMARKET_API_HOME /opt/app

RUN mkdir -p /opt/app

COPY . /opt/app
WORKDIR /opt/app

RUN ./gradlew build

ENTRYPOINT /opt/app/docker/startup.sh

EXPOSE 9090
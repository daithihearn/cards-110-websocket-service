# Websocket Service
Broker for websocket messages built for [Cards 110 application](https://github.com/daithihearn/cards-110)

# Requirements
To run this application you need a Redis instance to point to.
You will also need an application and API configured in Auth0.

All of the above can be configured in the `.env` file.

# Technical Stack
- Kotlin
- Spring-Boot
- Swagger

# Building
To build locally run `./gradlew clean build`
To build the docker image run `docker build . -t websocket-service`

# Running
To run locally built docker image run `docker run -d -p 7070:8080 --env-file .env --name websocket-service websocket-service`
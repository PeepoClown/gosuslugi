#!/bin/bash

docker-compose up -d postgres
./gradlew build
./gradlew war
./gradlew update
docker-compose up mydocs departs-coop

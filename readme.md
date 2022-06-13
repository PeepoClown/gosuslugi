# gov-services
It's my testing task to company digital league.

## Summary
As part of the task, I needed to implement a system consisting of 2 services. From a business point of view,
these services provided government services. The first is the paperwork, the second is the distribution of
documents by functional departments.

## Build
To build app run
```shell
docker-compose up
./gradle war
./gradle update
```
Or launch `start.sh` file.

## Stack
* Java11
* Spring5(not SpringBoot2)
* Gradle
* War
* Docker/Docker-compose
* Postgresql
* Liquibase
* REST

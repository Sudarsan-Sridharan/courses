#!/usr/bin/env bash

docker images -a;
docker ps -a;
docker volume ls;
docker network ls;
docker network inspect courses_net;
docker info;
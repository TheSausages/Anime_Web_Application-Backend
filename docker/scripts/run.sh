#!/bin/sh

#Must be run in Server folder

#build again with static content
SERVE_STATIC_CONTENT=serve gradle clean build

#Use docker compose to set all up, with the anime app using the fresh files
docker-compose -f docker/docker-compose-all.yml up --build
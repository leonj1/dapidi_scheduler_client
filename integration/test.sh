#!/bin/bash

export CORE_NETWORK=core_net
export PROJECT=scheduler_agent
export container=${PROJECT}; docker stop $container; docker rm $container

docker run -d --name ${PROJECT} \
-p 3232:3232 \
-e SERVERHOST=scheduler \
-e APP_PROFILE=integration \
--net ${CORE_NETWORK} \
dapidi/${PROJECT}:0.1

echo "Waiting for ${PROJECT} to come online"
while ! netstat -tna | grep 'LISTEN\>' | grep -q '.3232'; do
  sleep 5
done

sleep 10

curl http://localhost:5676/public/health


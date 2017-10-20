#!/bin/bash

# Default assumption is that its running on MBP for local dev
SERVERPORT=${SERVERPORT:=3232}
FAILED_RETRY_SECONDS=${FAILED_RETRY_SECONDS:=15}
HEALTHCHECK_INTERVAL_SECONDS=${HEALTHCHECK_INTERVAL_SECONDS:=60}
SERVERHOST=${SERVERHOST:=localhost}
SERVERPROTOCOL=${SERVERPROTOCOL:=http}
CLIENTPORT=${CLIENTPORT:=3232}
CLIENTHOST=${CLIENTHOST:=localhost}
APP_PROFILE=${APP_PROFILE:=production}

java -jar /agent.jar \
    --http.server.port=${SERVERPORT} \
    --spring.profiles.active=${APP_PROFILE} \
    --failed.job.retry.interval.seconds=${DBPASS} \
    --healthcheck.interval.seconds=${HEALTHCHECK_INTERVAL_SECONDS} \
    --server.host=${SERVERHOST} \
    --server.protocol=${SERVERPROTOCOL} \
    --client.port=${CLIENTPORT} \
    --client.host=${CLIENTHOST}

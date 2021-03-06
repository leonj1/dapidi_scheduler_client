FROM anapsix/alpine-java

MAINTAINER Jose Leon <leonj1@gmail.com>

RUN apk update && \
    apk add bash bash-doc bash-completion mysql-client heimdal-telnet

ADD target/agent-1.0-SNAPSHOT.jar /agent.jar
ADD bootstrap.sh /

ENTRYPOINT ["/bootstrap.sh"]


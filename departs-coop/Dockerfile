FROM alpine:latest
LABEL maintainer="dvasev@phoenixit.ru"

# update package manager dependencies and system
RUN apk update --no-cache && \
	apk upgrade --no-cache

# install jdk
ENV JAVA_HOME=/usr/lib/jvm/java-11-openjdk
RUN apk --no-cache add openjdk11 --repository=http://dl-cdn.alpinelinux.org/alpine/edge/community && \
    JAVA_HOME=$JAVA_HOME && \
    export JAVA_HOME && \
	export PATH="${PATH}:${JAVA_HOME}/bin"

# install tomcat
ENV TOMCAT_MAJOR=9 \
    TOMCAT_VERSION=9.0.46 \
    CATALINA_HOME=/opt/tomcat
RUN mkdir ${CATALINA_HOME} && \
    wget http://www-eu.apache.org/dist/tomcat/tomcat-${TOMCAT_MAJOR}/v${TOMCAT_VERSION}/bin/apache-tomcat-${TOMCAT_VERSION}.tar.gz && \
    tar -xvzf apache-tomcat-${TOMCAT_VERSION}.tar.gz --strip-components 1 --directory /opt/tomcat && \
    chmod -R 755 ${CATALINA_HOME} && \
    wget https://jdbc.postgresql.org/download/postgresql-42.2.18.jar && \
    mv postgresql-42.2.18.jar ${CATALINA_HOME}/lib/postgresql-42.2.18.jar && \
    chmod 755 ${CATALINA_HOME}/lib/postgresql-42.2.18.jar

RUN echo "Europe/Moscow" > /etc/timezone

# clean
RUN rm -rf /tmp/* /var/cache/apk/* && \
    rm -rf apache-tomcat-${TOMCAT_VERSION}.tar.gz

# setup
COPY ./build/libs/departs-coop.war  ${CATALINA_HOME}/webapps
EXPOSE 8080
ENTRYPOINT ${CATALINA_HOME}/bin/catalina.sh run
WORKDIR $CATALINA_HOME

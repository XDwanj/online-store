FROM openjdk:17

MAINTAINER XDwanj xdwanj@qq.com

ENV WORKDIR="/workdir"
ENV ONLINESTORE_CONFIGDIR="config"
WORKDIR $WORKDIR
COPY target/*.jar $WORKDIR/app.jar
EXPOSE 15000
ENTRYPOINT ["java", "-Xmx512m", "-jar", "app.jar"]

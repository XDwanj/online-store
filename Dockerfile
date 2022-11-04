FROM openjdk:17

MAINTAINER XDwanj xdwanj@qq.com

ENV WORKDIR="/workdir"
ENV ONLINESTORE_CONFIGDIR="config"
#ENV TZ=Asia/Shanghai
#ENV LC_ALL=zh_CN.UTF-8
WORKDIR $WORKDIR
COPY target/*.jar $WORKDIR/app.jar
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
EXPOSE 15000
ENTRYPOINT ["java", "-Xmx512m", "-jar", "app.jar"]

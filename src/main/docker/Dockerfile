FROM java:8
EXPOSE 8887
VOLUME /tmp
# 作者信息
MAINTAINER "zpc"
ADD community-0.0.1-SNAPSHOT.jar majiang.jar
RUN sh -c 'touch /majiang.jar'
ENV JAVA_OPTS=""
# ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /majiang.jar" ]
ENTRYPOINT ["java","-jar","majiang.jar"]
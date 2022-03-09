FROM openjdk:8-jdk
VOLUME /tmp
ADD config-0.0.1-SNAPSHOT.jar config.jar
ENTRYPOINT ["nohup","java","-Djava.security.egd=file:/dev/./urandom","-Xms80M -Xmx80M ","-jar","/app.jar",">/dev/null 2>&1 &"]
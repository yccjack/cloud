FROM openjdk:11-jdk
VOLUME /tmp
ADD auth-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["nohup","java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar",">/dev/null 2>&1 &"]
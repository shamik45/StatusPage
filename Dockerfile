FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Xmx256m","-Xms256m", "-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
FROM openjdk:11-jdk-slim

RUN adduser spring

COPY target/ehealthcare-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

USER spring:spring

ENTRYPOINT ["java", "jar", "app.jar"]

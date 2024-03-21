FROM openjdk:17-jdk-alpine

MAINTAINER bartmilo.io
LABEL authors="bart"

COPY target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
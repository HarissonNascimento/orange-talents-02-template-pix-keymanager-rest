FROM openjdk:11

MAINTAINER Harisson do Nascimento Pires

ARG JAR_FILE=build/libs/*-all.jar
ADD ${JAR_FILE} application.jar

ENV APP_NAME keymanager-rest

ENTRYPOINT ["java", "-jar", "/application.jar"]
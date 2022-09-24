FROM openjdk:14-jdk-alpine
MAINTAINER Savichev Aleksandr
COPY target/evotor-0.0.1-SNAPSHOT.jar evotor.jar
ENTRYPOINT ["java", "-jar", "/evotor.jar"]
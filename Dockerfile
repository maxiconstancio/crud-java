FROM openjdk:8-jdk-alpine
MAINTAINER baeldung.com
COPY target/crudjava-0.0.1-SNAPSHOT.jar service.jar
EXPOSE 8086
ENTRYPOINT ["java", "-jar", "/service.jar"]
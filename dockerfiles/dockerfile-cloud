FROM maven:3.5-jdk-11 AS build

COPY src /usr/src/app/src

COPY pom.xml /usr/src/app

RUN mvn -f /usr/src/app/pom.xml clean package

FROM openjdk:11-jre-slim

VOLUME /tmp

EXPOSE 8080

COPY --from=build /usr/src/app/target/td-0.0.1-SNAPSHOT.jar /td.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dserver.port=8080","-jar","/td.jar"]

FROM openjdk:8-jdk-alpine

LABEL maintainer="selimssevgi@gmail.com"

# Add a volume pointing to /tmp, spring writes to it
VOLUME /tmp

# Make port 8080 available to the world outside this container
EXPOSE 8080

# The application's jar file
ARG JAR_FILE=target/td-0.0.1-SNAPSHOT.jar

# Add the application's jar to the container
ADD ${JAR_FILE} td.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/td.jar"]

# docker image build -t todarch/td:latest -f dockerfiles/Dockerfile .
# docker container run -it --rm --name todarch-td -p 8081:8080 todarch/td
# localhost:8081/non-secured/up

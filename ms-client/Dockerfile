FROM openjdk:17
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} image-ms-client.jar
ENTRYPOINT ["java","-jar","/image-ms-client.jar"]
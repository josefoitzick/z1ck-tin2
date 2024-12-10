FROM openjdk:17
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} image-ms-credittracking.jar
ENTRYPOINT ["java","-jar","/image-ms-credittracking.jar"]
FROM openjdk:17
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} image-ms-credit.jar
ENTRYPOINT ["java","-jar","/image-ms-credit.jar"]
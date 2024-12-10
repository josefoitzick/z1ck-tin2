FROM openjdk:17
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} image-ms-credit-request.jar
ENTRYPOINT ["java","-jar","/image-ms-credit-request.jar"]
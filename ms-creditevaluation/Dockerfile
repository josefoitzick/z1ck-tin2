FROM openjdk:17
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} image-ms-credit-evaluation.jar
ENTRYPOINT ["java","-jar","/image-ms-credit-evaluation.jar"]
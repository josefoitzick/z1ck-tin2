FROM openjdk:17
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} image-ms-creditsimulation.jar
ENTRYPOINT ["java","-jar","/image-ms-creditsimulation.jar"]
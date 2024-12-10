FROM openjdk:17
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} image-ms-credittotalcost.jar
ENTRYPOINT ["java","-jar","/image-ms-credittotalcost.jar"]
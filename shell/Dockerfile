FROM openjdk:8-jdk-alpine
ARG JAR_FILE=messenger-*.jar
COPY ${JAR_FILE} messenger-server.jar
ENTRYPOINT ["java","-jar","messenger-server.jar"]
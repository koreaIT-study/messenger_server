FROM openjdk:8-jdk-alpine
ARG JAR_FILE=messenger_server*.jar
COPY ${JAR_FILE} messenger_server.jar
ENTRYPOINT ["java", "-jar", "-Duser.timezone=Asia/Seoul", "messenger_server.jar"]
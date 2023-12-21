FROM openjdk:17-jdk-slim
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} simple-user-manager-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "simple-user-manager-0.0.1-SNAPSHOT.jar"]
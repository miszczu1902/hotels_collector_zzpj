FROM maven:3.8.4-openjdk-17-slim

RUN apt-get update && \
    apt-get install -y docker

ARG JAR_FILE
COPY  ${JAR_FILE} "app.jar"

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]

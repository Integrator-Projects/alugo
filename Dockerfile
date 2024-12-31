FROM openjdk:21-jdk
LABEL authors="karla"

COPY target/alugo-0.0.1-SNAPSHOT.jar /app/app.jar

CMD ["java", "-jar", "/app/app.jar"]


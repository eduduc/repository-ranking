FROM openjdk:21-jdk-slim

RUN mkdir /app

WORKDIR /app

COPY target/repository-ranking-*.jar /app/repository-ranking.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "repository-ranking.jar"]

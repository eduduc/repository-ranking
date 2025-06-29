FROM maven:3.9.10-eclipse-temurin-21-alpine AS builder
WORKDIR /app
COPY . /app
RUN mvn clean install

FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=builder /app/target/repository-ranking-*.jar /app/repository-ranking.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Xmx2048M", "-jar", "repository-ranking.jar"]

FROM openjdk:21-slim
RUN mkdir /app
COPY target/repository-ranking-*.jar ./repository-ranking.jar
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -Duser.timezone=GMT -Dserver.port=8080 $JAVA_AGENT -jar /repository-ranking.jar
EXPOSE 8080

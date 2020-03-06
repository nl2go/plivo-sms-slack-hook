FROM openjdk:11

EXPOSE 8080

COPY ./target/plivo-sms-slack-hook-api-*.jar /app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]

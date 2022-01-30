FROM openjdk:8-jre-alpine
RUN apk update
RUN apk upgrade

ADD notes-service/target/notes-service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 5432
ENTRYPOINT exec java -jar -Djavax.net.debug=ssl /app.jar

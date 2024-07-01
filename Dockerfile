FROM openjdk:17-jre-slim
VOLUME /tmp
COPY target/ActiveViam-0.0.1-SNAPSHOT.jar.original app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
FROM eclipse-temurin:17-jre

WORKDIR /app

COPY target/hello-jenkins-1.0-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

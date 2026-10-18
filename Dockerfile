FROM eclipse-temurin:21-jdk-alpine
LABEL authors="Rosana"
COPY target/spring_zavrsni_zadatak-0.0.1-SNAPSHOT.jar demo.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/demo.jar"]
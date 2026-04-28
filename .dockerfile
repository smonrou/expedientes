FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /app

COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .

RUN chmod +x mvnw
RUN ./mvnw -q -e -DskipTests dependency:go-offline

COPY src src

RUN ./mvnw clean package -DskipTests


FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app


COPY --from=builder /app/target/expedientes-0.0.1-SNAPSHOT.jar app.jar


COPY certs/ca.pem /app/certs/ca.pem

ENV DB_URL=""
ENV DB_USER=""
ENV DB_PASSWORD=""


ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -XX:InitialRAMPercentage=50.0 -XX:+UseG1GC -XX:+UseStringDeduplication"

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
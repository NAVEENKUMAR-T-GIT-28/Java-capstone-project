# Stage 1: compile the Spring Boot executable JAR with Maven and Java 17.
FROM maven:3.8-openjdk-17 AS build
WORKDIR /workspace
COPY pom.xml .
RUN mvn -B -q dependency:go-offline
COPY src ./src
RUN mvn -B -DskipTests package

# Stage 2: run only the packaged JAR in a smaller Java runtime image.
FROM openjdk:17-slim AS runtime
WORKDIR /app
ENV SERVER_PORT=8080
COPY --from=build /workspace/target/smart-clinic-management-system-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75.0", "-jar", "/app/app.jar"]

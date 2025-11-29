# syntax=docker/dockerfile:1
# Multi-stage Dockerfile for Spring Boot application
# Build stage compiles the application, runtime stage creates a lightweight production image
# Uses BuildKit cache mount to speed up Maven dependency downloads

# Build stage
FROM maven:3.9-eclipse-temurin-17-alpine AS build
WORKDIR /app

# Download dependencies with cache mount (much faster on rebuilds)
COPY pom.xml .
RUN --mount=type=cache,target=/root/.m2 mvn dependency:go-offline -B

# Build application
COPY src ./src
RUN --mount=type=cache,target=/root/.m2 mvn clean package -DskipTests -Dspotless.check.skip=true

# Runtime stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Run as non-root user
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copy built JAR from build stage
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

# Health check for container orchestration
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

ENTRYPOINT ["java", "-jar", "app.jar"]
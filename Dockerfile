# Multi-stage build for Spring Boot application
# Using Maven 3.9 with Java 17 for reproducibility
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code and build
COPY src ./src
RUN mvn clean package -DskipTests -Dspotless.check.skip=true

# Runtime stage
# Using Java 17 LTS for reproducibility (supports both ARM64 and AMD64)
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Install wget for healthcheck
RUN apt-get update && apt-get install -y --no-install-recommends wget && rm -rf /var/lib/apt/lists/*

# Copy the built JAR from build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health 2>/dev/null || exit 1

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]


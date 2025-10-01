# Stage 1: Build the application
# Maven image with temurin 17
FROM maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /app

# The following argument is copied from your original file
ARG SPRING_PROFILES_ACTIVE

# Copy project files
COPY pom.xml .
COPY src/ /app/src/
# Download dependencies first to leverage Docker layer caching
RUN mvn dependency:go-offline

# Build the final JAR
RUN mvn clean package -U -DskipTests

# Stage 2: Create the final image
# Using a slim JRE based on Temurin 17 (Long Term Support)
FROM eclipse-temurin:17-jre

WORKDIR /app
# Copy the compiled application JAR from the builder stage
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]
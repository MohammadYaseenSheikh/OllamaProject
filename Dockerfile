# Use an official Java 21 runtime as a parent image
FROM eclipse-temurin:21-jdk AS builder

# Set the working directory
WORKDIR /app

# Copy Maven or Gradle build files (adjust based on your build tool)
COPY mvnw pom.xml ./
COPY src src

# Use a minimal JDK runtime for production
FROM eclipse-temurin:21-jre AS runtime

WORKDIR /app

# Copy the built JAR from the previous stage
COPY --from=builder /app/target/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]
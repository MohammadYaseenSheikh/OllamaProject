# Build stage
FROM eclipse-temurin:21-jdk AS builder
WORKDIR /app
COPY . .

# Runtime stage
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
CMD ["java", "-jar", "app.jar"]
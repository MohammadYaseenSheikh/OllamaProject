# Build stage
FROM bellsoft/liberica-openjdk-debian:23 AS builder
WORKDIR /app
COPY . .

# Runtime stage
FROM bellsoft/liberica-openjdk-debian:23
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
CMD ["java", "-jar", "app.jar"]
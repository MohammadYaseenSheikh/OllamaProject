# Build stage
FROM eamazoncorretto:23 AS builder
WORKDIR /app
COPY . .

# Runtime stage
FROM amazoncorretto:23
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
CMD ["java", "-jar", "app.jar"]
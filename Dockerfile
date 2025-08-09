# Stage 1: Build the JAR with Java 23 and Maven
FROM bellsoft/liberica-openjdk-debian:23 AS build
WORKDIR /app

# Install Maven
RUN apt-get update && \
    apt-get install -y maven && \
    rm -rf /var/lib/apt/lists/*

COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Run the app
FROM bellsoft/liberica-openjdk-debian:23
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
CMD ["java", "-jar", "app.jar"]
# Use Liberica JDK 23 base image
FROM bellsoft/liberica-openjdk-debian:23

# Set working directory
WORKDIR /app

# Copy source code
COPY . .

# Ensure mvnw is executable and has correct line endings
RUN apt-get update && apt-get install -y dos2unix \
    && dos2unix mvnw \
    && chmod +x mvnw

# Build the JAR (assumes Maven is used)
RUN ./mvnw clean package -DskipTests

# Run the application
CMD ["java", "-jar", "target/app.jar"]
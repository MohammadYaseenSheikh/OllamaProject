# Use Liberica JDK 23 base image
FROM bellsoft/liberica-openjdk-debian:23

# Set working directory
WORKDIR /app

# Install Maven
RUN apt-get update && apt-get install -y maven

# Copy source code
COPY . .

# Build the JAR using system Maven
RUN mvn clean package -DskipTests

# Run the application
CMD ["java", "-jar", "target/app.jar"]
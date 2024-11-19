# Dockerfile for Spring Boot
# Use a base OpenJDK image using java 21
FROM openjdk:22

# Set the working directory
WORKDIR /app

# Copy the JAR file
COPY target/BeBSystem-backend-0.0.1-SNAPSHOT.jar /app/app.jar

# Expose the backend port
EXPOSE 8080

# Run the Spring Boot application
CMD ["java", "-jar", "app.jar"]

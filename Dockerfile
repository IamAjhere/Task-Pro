# Use the official OpenJDK base image
FROM openjdk:17-jdk-slim
  
  # Set the working directory in the container
WORKDIR /app
  
  # Copy the packaged jar file into the container
COPY target/taskpro-1.0.0.jar /app
  
  # Expose the port the app runs on
EXPOSE 8080
  
  # Command to run the application
CMD ["java", "-jar", "taskpro-1.0.0.jar"]
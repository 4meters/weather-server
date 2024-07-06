# Use a Maven image to build the application
FROM maven:3.8.5-openjdk-11-slim AS build

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml file and the source code into the container
COPY pom.xml .
COPY src ./src

# Package the application to generate the JAR file
RUN mvn clean package

# Use a lighter base image for the final run stage
FROM openjdk:11-jre-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/*.jar /app/server-0.0.1-SNAPSHOT.jar

# Expose port 8080
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "server-0.0.1-SNAPSHOT.jar"]

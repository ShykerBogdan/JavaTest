FROM maven:3.8.5-openjdk-11 AS build

# Set the working directory
WORKDIR /app

# Copy the pom.xml file
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline -B

# Copy the project source
COPY src ./src

# Package the application
RUN mvn package -DskipTests

# Debug image
FROM openjdk:11-jdk

# Install necessary tools for debugging
RUN apt-get update && apt-get install -y curl procps

# Set the working directory
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port the app runs on
EXPOSE 8080

# Expose debug port
EXPOSE 5005

# Add wait script for waiting for database
ADD https://github.com/ufoscout/docker-compose-wait/releases/download/2.9.0/wait /wait
RUN chmod +x /wait

# Command to run the application with debug enabled
CMD /wait && java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005 -jar app.jar

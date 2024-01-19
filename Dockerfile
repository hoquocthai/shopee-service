# FROM maven:3.8-openjdk-11 AS build
# COPY . .
# RUN mvn clean package -DskipTests

# FROM openjdk:11-jdk-slim-buster
# COPY --from=build /target/shopee-service-0.0.1-SNAPSHOT.jar shopee-service.jar
# EXPOSE 8000
# ENTRYPOINT ["java","-jar","shopee-service.jar"]

# Use OpenJDK 11 as the base image
FROM openjdk:11

# Set the working directory
WORKDIR /app

# Copy the Maven wrapper and the project POM file
COPY mvnw .
COPY mvnw.cmd .
COPY pom.xml .

# Copy the entire project
COPY src ./src

# Build the application
RUN ./mvnw clean install -DskipTests

# Expose the port your app will run on
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "target/stripe-service-0.0.1-SNAPSHOT.jar"]

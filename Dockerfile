FROM maven:3.8-openjdk-11 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:11-jdk-slim-buster
COPY --from=build /target/stripe-service-0.0.1-SNAPSHOT.jar stripe-service.jar
EXPOSE 8000
ENTRYPOINT ["java","-jar","stripe-service.jar"]

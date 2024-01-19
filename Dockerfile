FROM maven:3.8-openjdk-11 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:11-jdk-slim-buster
COPY --from=build /target/shopee-service-0.0.1-SNAPSHOT.jar shopee-service.jar
EXPOSE 8000
ENTRYPOINT ["java","-jar","shopee-service.jar"]
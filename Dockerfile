
FROM maven:3.8.4-openjdk-21 AS build

WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests


FROM openjdk:22-jdk-slim
WORKDIR /app
COPY --from=build /app/target/patient-pdf-service.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

# Usa uma imagem do Maven com OpenJDK 21 para compilar a aplicação
FROM maven:3.8.4-openjdk-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Usa uma imagem JRE para rodar a aplicação
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=build /app/target/patient-pdf-service.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]



FROM maven:3.8.4-openjdk-22 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests


FROM eclipse-temurin:22-jre-slim
WORKDIR /app
COPY --from=build /app/target/suaplicacao.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

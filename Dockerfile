# Stage 1: Build the application
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/app.jar app.jar
# Hugging Face Spaces requires port 7860
EXPOSE 7860
ENV PORT=7860
ENTRYPOINT ["java", "-jar", "app.jar"]

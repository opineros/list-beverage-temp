#Stage 1: build the Spring Boot gateway using the Gradle wrapper
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /list-beverage
COPY . .
RUN chmod +x ./gradlew && ./gradlew :gateway-service:bootJar -x test --no-daemon

#Stage 2: runtime image
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY --from=builder /list-beverage/gateway-service/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
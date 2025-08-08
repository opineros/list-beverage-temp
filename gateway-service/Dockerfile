# Stage 1: compile and build jar
FROM gradle:8-jdk21 AS builder
WORKDIR /home/gradle/project
COPY . .
RUN gradle bootJar --no-daemon

# 2) Etapa de runtime
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY --from=builder /workspace/gateway-service/build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
# Stage 1: Build with Gradle
FROM gradle:8.5-jdk21 AS build
WORKDIR /home/gradle/project
COPY --chown=gradle:gradle . .
RUN gradle clean bootJar --no-daemon

# Stage 2: Runtime image
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
# Copiamos el JAR generado
COPY --from=build /home/gradle/project/build/libs/*.jar app.jar
# Puerto por defecto de Eureka
EXPOSE 8761
ENTRYPOINT ["java","-jar","/app/app.jar"]
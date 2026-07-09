# --- Etapa 1: build ---
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
 
# Copiamos primero el pom.xml para aprovechar la cache de capas de Docker
COPY pom.xml .
RUN mvn -B dependency:go-offline
 
# Ahora copiamos el código fuente y compilamos
COPY src ./src
RUN mvn -B clean package -DskipTests
 
# --- Etapa 2: imagen final, liviana ---
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
 
COPY --from=build /app/target/*.jar app.jar
 
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
 
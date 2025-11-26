# Étape 1 : Builder
FROM maven:3.9.2-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Étape 2 : Application
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Arguments pour build
ARG POSTGRES_HOST
ARG POSTGRES_PORT
ARG POSTGRES_DB
ARG POSTGRES_USER
ARG POSTGRES_PASSWORD

# Variables d'environnement pour l'exécution
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://$POSTGRES_HOST:$POSTGRES_PORT/$POSTGRES_DB
ENV SPRING_DATASOURCE_USERNAME=$POSTGRES_USER
ENV SPRING_DATASOURCE_PASSWORD=$POSTGRES_PASSWORD

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]

# ---- Build stage ----
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
ENV MAVEN_ARGS=""
ENV MAVEN_OPTS=""
RUN chmod +x mvnw && ./mvnw -B clean package -DskipTests

# ---- Run stage ----
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.war app.war
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.war"]
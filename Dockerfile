# ===================================================
# Stage 1: Build Java Spring Boot Application
# ===================================================
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY backend/pom.xml .
RUN mvn dependency:go-offline -B
COPY backend/src ./src
RUN mvn clean package -DskipTests

# ===================================================
# Stage 2: Minimal Production JRE Image
# ===================================================
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Create non-root secure user
RUN groupadd -r financeflow && useradd -r -g financeflow appuser

COPY --from=build /app/target/finance-tracker-1.0.0.jar app.jar
RUN chown -R appuser:financeflow /app
USER appuser

EXPOSE 8002

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]

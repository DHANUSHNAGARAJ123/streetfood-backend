# Stage 1: Build the application using Maven
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY . .
# Maven vachu project-ah clean panni package (jar) pandrom
RUN mvn clean package -DskipTests

# Stage 2: Run the application using a lightweight JDK image
FROM openjdk:17-jdk-slim
WORKDIR /app
# Pom.xml-la irukura details vachu correct-ah copy pandrom
COPY --from=build /app/target/backend-1.0.0.jar app.jar

# Port 8080-ah expose pandrom (Render default port)
EXPOSE 8080

# App-ah start panna command
ENTRYPOINT ["java", "-jar", "app.jar"]

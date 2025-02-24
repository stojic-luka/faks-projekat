FROM maven:3.8.7-openjdk-18-slim as build

WORKDIR /app
COPY pom.xml /app
RUN mvn dependency:go-offline -B
COPY src /app/src
RUN mvn clean package -DskipTests -e

FROM openjdk:18-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

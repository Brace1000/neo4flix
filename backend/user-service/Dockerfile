FROM maven:3.9.5-eclipse-temurin-17 AS build
WORKDIR /build
COPY common/pom.xml ./common/pom.xml
COPY common/src ./common/src
WORKDIR /build/common
RUN mvn clean install -DskipTests
WORKDIR /build/service
COPY user-service/pom.xml .
COPY user-service/src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /build/service/target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "--add-opens=java.base/java.lang=ALL-UNNAMED", "--add-opens=java.base/java.lang.reflect=ALL-UNNAMED", "-jar", "app.jar"]

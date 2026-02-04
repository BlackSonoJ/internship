FROM gradle:8-jdk21 AS build
LABEL authors="vladimirligatiuk"
WORKDIR /notification-service

COPY gradle gradle
COPY gradlew .
COPY build.gradle .
COPY settings.gradle .

COPY src src

RUN chmod +x ./gradlew
RUN ./gradlew clean build -x test --no-daemon

FROM eclipse-temurin:21-jre
WORKDIR /notification-service
COPY --from=build /notification-service/build/libs/*.jar notification-service.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "notification-service.jar"]
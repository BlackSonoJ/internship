FROM gradle:8-jdk21 AS build
LABEL authors="vladimirligatiuk"
WORKDIR /config-server

COPY gradle gradle
COPY gradlew .
COPY build.gradle .
COPY settings.gradle .

COPY src src

RUN chmod +x ./gradlew
RUN ./gradlew clean build -x test --no-daemon

FROM eclipse-temurin:21-jre-alpine
WORKDIR /config-server
COPY --from=build /config-server/build/libs/*.jar config-server.jar

EXPOSE 8888
ENTRYPOINT ["java", "-jar", "config-server.jar"]
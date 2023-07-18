FROM maven AS build
WORKDIR /app
COPY pom.xml /app
COPY src/main /app/src/main
RUN mvn package

FROM openjdk:17-slim
WORKDIR /app
COPY --from=build /app/target /app
CMD ["java", "-jar", "biss-ctf-back-release.jar"]

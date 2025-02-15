FROM gradle AS build
WORKDIR /app
COPY . /app
RUN gradle build --no-daemon

FROM openjdk:17-slim AS runner
WORKDIR /app
COPY --from=build /app/build/libs/backend-LATEST.jar /app/app.jar
CMD ["java", "-jar", "app.jar"]


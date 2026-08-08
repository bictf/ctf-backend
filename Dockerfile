FROM gradle:8.7 AS build
WORKDIR /app
COPY . /app
RUN gradle build --no-daemon

FROM amazoncorretto:17 AS runner
WORKDIR /app
COPY --from=build /app/build/libs/backend-LATEST.jar /app/app.jar
CMD ["java", "-jar", "app.jar"]

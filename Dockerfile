FROM gradle:jdk11 as builder

COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM openjdk:11-slim as runtime
EXPOSE 8080

RUN mkdir /app

COPY --from=builder /home/gradle/src/build/libs/*.jar /app/app.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=docker","-jar","/app/app.jar"]
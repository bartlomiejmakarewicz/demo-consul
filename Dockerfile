FROM azul/zulu-openjdk-alpine:11 as build

WORKDIR /build
COPY .mvn/ .mvn/
COPY mvnw .
COPY pom.xml .
RUN ./mvnw -B dependency:go-offline
COPY src/ /build/src
RUN ./mvnw -B package

FROM azul/zulu-openjdk-alpine:11-jre

COPY --from=build /build/target/*.jar /app/application.jar
EXPOSE 8080
WORKDIR /app

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/application.jar"]

FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./

RUN chmod +x mvnw

RUN ./mvnw -q dependency:resolve

COPY src ./src

RUN ./mvnw -q clean package -DskipTests

EXPOSE 10000

# OJO: el nombre del jar debe coincidir con el que genera Maven en /target
CMD ["java", "-jar", "target/Inmobivalor_BE-0.0.1-SNAPSHOT.jar"]

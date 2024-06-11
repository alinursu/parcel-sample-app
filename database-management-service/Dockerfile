FROM maven:3.9.6-eclipse-temurin-22-alpine

WORKDIR "/tmp"

# Build and run app
RUN mkdir -p app_dbms
COPY . app_dbms
WORKDIR "/tmp/app_dbms"

RUN mvn clean install

RUN cp ./target/*.jar app.jar

ENTRYPOINT ["java","-jar","/tmp/app_dbms/app.jar"]
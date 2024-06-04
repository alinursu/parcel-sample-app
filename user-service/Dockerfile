FROM maven:3.9.6-eclipse-temurin-22-alpine

# Build and link dependencies
WORKDIR "/tmp"

RUN mkdir -p dependency-dbms
COPY ../database-management-service dependency-dbms
WORKDIR "/tmp/dependency-dbms"

RUN mvn clean compile assembly:single
RUN mvn install:install-file -Dfile=./target/database-management-service-1.0.0-jar-with-dependencies.jar -DgroupId=ro.uaic.info.parcel -DartifactId=database-management-service -Dversion=1.0.0 -Dpackaging=jar -DgeneratePom=true

# Build and run app
WORKDIR "/tmp"

RUN mkdir app-usrs
COPY ../user-service app-usrs
WORKDIR "/tmp/app-usrs"

RUN mvn clean install
RUN cp ./target/*.jar app.jar

ENTRYPOINT ["java","-jar","/tmp/app-usrs/app.jar"]
FROM maven:3.9.6-eclipse-temurin-22-alpine

# Build and link dependencies
WORKDIR "/tmp"

RUN mkdir -p dependency-dbms
COPY ../database-management-service dependency-dbms
WORKDIR "/tmp/dependency-dbms"

RUN mvn clean compile assembly:single
RUN mvn install:install-file -Dfile=./target/database-management-service-1.0.0-jar-with-dependencies.jar -DgroupId=ro.uaic.info.parcel -DartifactId=database-management-service -Dversion=1.0.0 -Dpackaging=jar -DgeneratePom=true

WORKDIR "/tmp"

RUN mkdir -p dependency-usrs
COPY ../user-service dependency-usrs
WORKDIR "/tmp/dependency-usrs"

RUN mvn clean compile assembly:single
RUN mvn install:install-file -Dfile=./target/user-service-1.0.0-jar-with-dependencies.jar -DgroupId=ro.uaic.info.parcel -DartifactId=user-service -Dversion=1.0.0 -Dpackaging=jar -DgeneratePom=true

WORKDIR "/tmp"

RUN mkdir -p dependency-pymnt
COPY ../payment-service dependency-pymnt
WORKDIR "/tmp/dependency-pymnt"

RUN mvn clean compile assembly:single
RUN mvn install:install-file -Dfile=./target/payment-service-1.0.0-jar-with-dependencies.jar -DgroupId=ro.uaic.info.parcel -DartifactId=payment-service -Dversion=1.0.0 -Dpackaging=jar -DgeneratePom=true

WORKDIR "/tmp"

RUN mkdir -p dependency-dlvry
COPY ../delivery-service dependency-dlvry
WORKDIR "/tmp/dependency-dlvry"

RUN mvn clean compile assembly:single
RUN mvn install:install-file -Dfile=./target/delivery-service-1.0.0-jar-with-dependencies.jar -DgroupId=ro.uaic.info.parcel -DartifactId=delivery-service -Dversion=1.0.0 -Dpackaging=jar -DgeneratePom=true

# Build and run app
WORKDIR "/tmp"

RUN mkdir app-gtwy
COPY ../gateway-app app-gtwy
WORKDIR "/tmp/app-gtwy"

RUN mvn clean install
RUN cp ./target/*.jar app.jar

ENTRYPOINT ["java","-jar","/tmp/app-gtwy/app.jar"]
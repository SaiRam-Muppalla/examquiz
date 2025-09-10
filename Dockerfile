# Multi-stage build for OES
FROM maven:3.9.6-eclipse-temurin-11 AS build
WORKDIR /app
COPY OES/pom.xml OES/pom.xml
RUN mvn -q -f OES/pom.xml dependency:go-offline
COPY OES /app/OES
RUN mvn -q -f OES/pom.xml package -DskipTests

# Runtime image with Tomcat
FROM tomcat:9.0-jdk11
# Remove default apps
RUN rm -rf /usr/local/tomcat/webapps/*
COPY --from=build /app/OES/target/OES.war /usr/local/tomcat/webapps/ROOT.war
# Environment variables for DB
ENV DB_HOST=db \
    DB_PORT=3306 \
    DB_NAME=OES_Project \
    DB_USER=root \
    DB_PASS=root \
    HIBERNATE_HBM2DDL=update \
    HIBERNATE_SHOW_SQL=true
EXPOSE 8080
CMD ["catalina.sh", "run"]

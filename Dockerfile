# Build: mvn clean package && docker build -t registration-app .
# Tomcat 10.1 = Jakarta Servlet 6.0, matching this project's jakarta.* imports.
FROM tomcat:10.1-jdk21-temurin
RUN rm -rf /usr/local/tomcat/webapps/*
COPY webapp/target/webapp.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080

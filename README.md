# Registration App

Maven multi-module registration app for **Java 21**.

- `server` – registration logic (validation, PBKDF2 password hashing, in-memory repository) + JUnit 5 tests
- `webapp` – Servlet 6 / JSP front end packaged as a WAR (runs on Tomcat 10.1+ or Jetty 12)

## Requirements
Java 21 and Maven 3.6.3+ (`mvn -v` must report Java 21).

## Build and test
```
mvn clean package
```
Outputs `server/target/server.jar` and `webapp/target/webapp.war`.

## Run
Jetty (no install needed):
```
mvn -pl webapp -am package jetty:run   # http://localhost:8080/
```
Docker (Tomcat 10.1 on JDK 21):
```
docker build -t registration-app .
docker run -p 8080:8080 registration-app
```

## Notes
Users are stored in memory and lost on restart; implement `UserRepository` with a database for persistence.

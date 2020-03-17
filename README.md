# PharmaPartners Assessment

Crypto currency API by Jasper Boot. Includes 5 endpoints to create, read, update and delete currencies. It fully 
features pagination and sorting on the `GET /currencies` endpoint and all requests and responses are logged. Logs will
be written to `logs/runtime.log` file.

## Build & Run

1. Run `./mvnw install` on Mac/Linux or `./mvnw.exe` on Windows.
2. Run `java -jar target/pp-0.0.1-SNAPSHOT.jar` to start the API
3. The API should be available at http://localhost:8080

## Documentation

Visit the swagger documentation at http://localhost:8080/swagger-ui.html. There you can find and test all available 
endpoints.

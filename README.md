# PharmaPartners Assessment

Crypto currency API by Jasper Boot. Includes 5 endpoints to create, read, update and delete currencies. It fully 
features pagination and sorting on the `GET /currencies` endpoint and all requests and responses are logged. Logs will
be written to `logs/runtime.log` file. Currencies represent their market cap and number of coins as strings, because 
traditional JavaScript frameworks cannot handle big integers so well.

## Build & Run

1. Run `./mvnw install` on Mac/Linux or `./mvnw.exe` on Windows.
2. Run `java -jar target/pp-0.0.1-SNAPSHOT.jar` to start the API
3. The API should be available at http://localhost:8080/api

## Documentation

Visit the swagger documentation at http://localhost:8080/api/swagger-ui.html. There you can find and test all available 
endpoints.

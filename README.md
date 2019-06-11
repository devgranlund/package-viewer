# package-viewer
Small program to list installed packages

### Build
mvn clean package

### Run web server
java -jar target/package-viewer-1.0-SNAPSHOT.jar

### HTTP API
localhost:8080 - get package listing  
localhost:8080/shutdown - shutdown the server

### Undertow unclean shutdown (Mac)
lsof -i:8080  
kill -9 *id

## Dependencies and rationale

### JUnit test framework
No need for rationale, right?

### Undertow web server
Modular, flexible and light-weight HTTP-server implementation.  
Too much work to do own implementation.

## Future considerations
1. FileReader - fix closing of resources
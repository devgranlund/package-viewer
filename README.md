# package-viewer
Small program to list currently installed packages

### Build
mvn clean package

### Run web server
java -jar target/package-viewer-1.0-SNAPSHOT.jar

### Undertow unclean shutdown (Mac)
lsof -i:8080  
kill -9 *id

## Dependencies and rationale
### Undertow web server
Modular, flexible and light-weight HTTP-server implementation.  
Too much work to do own implementation.
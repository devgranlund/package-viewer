# package-viewer
Small program to list installed packages

### Build
mvn clean package

### Run web server
java -jar target/package-viewer-1.0-SNAPSHOT.jar

### HTTP API
localhost:8080 - get package listing  
localhost:8080/shutdown - shutdown the server  
localhost:8080/packages/{packageName} - get details of one package

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
* ~~Fix security vulnerability in undertow-core 2.0.1 -> patch to 2.0.21~~ 
* FileReader - fix closing of resources
* ~~Fix fop -package~~
* ~~Fix libjs-jquery -package~~
* ~~Convert shutdown message to HTML~~
* ~~abstract base class for render-classes~~
* Generate bidirectional relationships
* Vagrant with real package listing
* PackageRendererTest
* ~~Fix uppstart-job --package -> fails~~

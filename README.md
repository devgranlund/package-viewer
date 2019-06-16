# package-viewer
Small program to list installed packages

### Vagrant
run: vagrant up  
end: vagrant destroy  
reload: vagrant reload --provision

#### HTTP API
localhost:8080 - get package listing  
localhost:8080/shutdown - shutdown the server  
localhost:8080/packages/{packageName} - get details of one package

## Future considerations
* Vagrant with real package listing
* FileReader - fix closing of resources
* More tests, 100% line coverage :)
* ~~Fix security vulnerability in undertow-core 2.0.1 -> patch to 2.0.21~~ 
* ~~Fix fop -package~~
* ~~Fix libjs-jquery -package~~
* ~~Convert shutdown message to HTML~~
* ~~Abstract base class for render-classes~~
* ~~Generate bidirectional relationships~~
* ~~PackageRendererTest~~
* ~~Fix uppstart-job --package -> fails~~

## Dependencies and rationale for use

### JUnit test framework
No need for rationale, right?

### Undertow web server
Modular, flexible and light-weight HTTP-server implementation.  
Too much work to do own implementation.

## Development related instructions
By default, program is run in dev/test -mode.  
Program can be executed in production -mode with command line parameter "prod"

#### Build and run -script (dev/test)
sh compile-and-run.sh

#### Build Java project manually
mvn clean package

#### Run project (web server) manually
java -jar target/package-viewer-1.0-SNAPSHOT.jar

#### Undertow unclean shutdown (Mac)
lsof -i:8080  
kill -9 *id



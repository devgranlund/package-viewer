#!/usr/bin/env bash
mvn clean package
java -jar target/package-viewer-1.0-SNAPSHOT.jar &
MyPID=$!
echo "Process id: " ${MyPID}
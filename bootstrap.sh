#!/usr/bin/env bash

apt-get update -y 
apt-get upgrade -y

## Install Java
add-apt-repository ppa:openjdk-r/ppa -y
apt-get update
apt-get -y install openjdk-8-jdk openjdk-8-jdk-headless maven
update-alternatives --config java <<< '2'

## Verify installation
java -version

## Build project
cd /vagrant
mvn clean package

## Start server in production mode
java -jar target/package-viewer-1.0-SNAPSHOT.jar prod


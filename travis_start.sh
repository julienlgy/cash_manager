#!/bin/bash
echo GOING TO BUILD SERVER
cd ./cashmanager/ 
./mvnw compile
java -jar ./target/cashmanager-0.0.1-SNAPSHOT.war
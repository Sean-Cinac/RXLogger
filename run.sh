#!/bin/bash

mvn -q clean compile || { echo "Compile failed"; exit 1; }

mvn -q dependency:build-classpath -Dmdep.outputFile=cp.txt || { echo "Failed to build classpath"; exit 1; }
CP=$(cat cp.txt):target/classes

java -cp "$CP" Main "$@"

#!/bin/bash

cd ./code
javac -d ../out/production *.java
cd ../out/production
java drive
#!/bin/sh

git clean -xfd

#env
echo "listing files"
ls -lrt
echo "Running maven $1"
mvn -B -DskipTests clean $1

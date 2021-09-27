#!/bin/sh

#git clean -xfd

#env
echo "Running maven $1"
mvn -B -DskipTests clean $1

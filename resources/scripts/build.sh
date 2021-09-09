#!/bin/sh

git clean -xfd

env
mvn -B -DskipTests clean package

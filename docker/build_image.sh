#!/bin/bash

cp ../build/libs/cniface-platform-*.jar .
docker build -t abelleeye/cniface-platform:v0.0.1 .
rm -rf ./*.jar

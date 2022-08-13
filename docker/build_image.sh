#!/bin/bash

cp ../build/libs/cniface-platform.jar .
docker build -t abelleeye/cniface-platform:v0.0.3 .
rm -rf ./*.jar

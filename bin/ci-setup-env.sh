#!/usr/bin/env bash

set -e

mkdir -p ~/.gradle

echo "BINTRAY_USERNAME=${BINTRAY_USERNAME}" >> ~/.gradle/gradle.properties
echo "BINTRAY_API_KEY=${BINTRAY_API_KEY}" >> ~/.gradle/gradle.properties

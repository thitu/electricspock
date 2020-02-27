#!/usr/bin/env bash

set -e

mkdir -p ~/.gradle

if [ -n "${TRAVIS_TAG}" ]; then
  if [ -n "${BINTRAY_USERNAME}" ] && [ -n "${BINTRAY_API_KEY}" ]; then
    echo "BINTRAY_USERNAME=${BINTRAY_USERNAME}" >> ~/.gradle/gradle.properties
    echo "BINTRAY_API_KEY=${BINTRAY_API_KEY}" >> ~/.gradle/gradle.properties
  else
    echo "Error: missing publishing secrets" && exit -1
  fi
else
  echo "BINTRAY_USERNAME=__BINTRAY_USERNAME__" >> ~/.gradle/gradle.properties
  echo "BINTRAY_API_KEY=__BINTRAY_API_KEY__" >> ~/.gradle/gradle.properties
fi

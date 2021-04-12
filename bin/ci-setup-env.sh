#!/usr/bin/env bash

set -e

mkdir -p ~/.gradle

if [ -n "${TRAVIS_TAG}" ]; then
  if [ -n "${JFROG_USERNAME}" ]; then
    echo "JFROG_USERNAME=${JFROG_USERNAME}" >> ~/.gradle/gradle.properties
    echo "JFROG_PASSWORD=${JFROG_PASSWORD}" >> ~/.gradle/gradle.properties
  else
    echo "Error: missing publishing secrets" && exit -1
  fi
else
  echo "JFROG_USERNAME=__JFROG_USERNAME__" >> ~/.gradle/gradle.properties
  echo "JFROG_PASSWORD=__JFROG_PASSWORD__" >> ~/.gradle/gradle.properties
fi

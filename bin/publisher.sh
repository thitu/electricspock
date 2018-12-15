#!/usr/bin/env bash

set -e

clear
./gradlew build publish bintrayUpload

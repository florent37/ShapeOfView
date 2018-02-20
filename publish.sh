#!/usr/bin/env bash
./gradlew clean assembleDebug install
./gradlew bintrayUpload
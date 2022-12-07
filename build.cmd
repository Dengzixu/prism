@echo off

set GRADLE_OPTS="-Dfile.encoding=utf-8"

call .\gradlew clean build -x test

pause
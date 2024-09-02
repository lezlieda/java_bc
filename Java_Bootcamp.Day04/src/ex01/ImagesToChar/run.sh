#!/usr/bin/bash

rm -rf target && mkdir target
javac `find . -name "*.java"` -d ./target
cp -r src/resources target/
jar -cmf src/manifest.txt target/images-to-chars-printer.jar -C target/ .
java -jar target/images-to-chars-printer.jar --black=$1 --white=$2
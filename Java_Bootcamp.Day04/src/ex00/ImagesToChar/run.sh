#!/usr/bin/bash

rm -rf target
mkdir target
javac `find . -name "*.java"` -d ./target
java -cp ./target edu.school21.printer.app.Program --black=$1 --white=$2 --path=$3
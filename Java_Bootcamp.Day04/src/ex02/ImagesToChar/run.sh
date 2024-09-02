#!/usr/bin/bash

rm -rf target && mkdir target
cp -r src/resources target/
jar -xf lib/jcommander-1.82.jar
jar -xf lib/JCDP-4.0.2.jar
mv com target/
mv META-INF target/
javac -cp lib/JCDP-4.0.2.jar:lib/jcommander-1.82.jar: `find src -name "*.java"` -d target
jar -cmf src/manifest.txt target/images-to-chars-printer.jar -C target/ .
java -jar target/images-to-chars-printer.jar --black=$1 --white=$2
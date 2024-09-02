1. Create target directory:
    `rm -rf target && mkdir target`
2. Copy the resources:
    `cp -r src/resources target/`
3. Unpack additional libraries and move them to the target directory:
    `jar -xf lib/jcommander-1.82.jar`
    `jar -xf lib/JCDP-4.0.2.jar`
    `mv com target/`
    `mv META-INF target/`
4. Compile the source code:
    `javac -cp lib/JCDP-4.0.2.jar:lib/jcommander-1.82.jar: `find src -name "*.java"` -d target`
5. Create the jar file:
    `jar -cmf src/manifest.txt target/images-to-chars-printer.jar -C target/ .`
5. Run the jar file:
    `java -jar target/images-to-chars-printer.jar --black=<COLOR> --white=<COLOR>`
    where `<COLOR>` is the color of the pixels used instead of black and white in the original image.
Colors can be: {BLACK, RED, GREEN, YELLOW, BLUE, MAGENTA, CYAN, WHITE, NONE}

...or simply run the `run.sh` script:
    `./run.sh <char> <char>`
    where:
    - `<char>` - character to represent black and white pixels.
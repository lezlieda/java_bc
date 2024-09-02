1. Create target directory:
    `rm -rf target && mkdir target`
2. Compile the code:
    `javac `find . -name "*.java"` -d ./target`
3. Copy the resources:
    `cp -r src/resources target/`
4. Create the jar file:
    `jar -cmf src/manifest.txt target/images-to-chars-printer.jar -C target/ .`
5. Run the jar file:
    `java -jar target/images-to-chars-printer.jar --black=<char> --white=<char>`
    where `<char>` is the character to be used for black and white pixels.

...or simply run the `run.sh` script:
    `./run.sh <char> <char>`
    where:
    - `<char>` - character to represent black and white pixels.
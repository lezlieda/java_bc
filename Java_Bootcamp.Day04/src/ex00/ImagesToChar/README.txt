1. Create target directory:
    `rm -rf target && mkdir target`
2. Compile the code:
    `javac `find . -name "*.java"` -d ./target`
3. Run the code:
    `java -cp ./target edu.school21.printer.app.Program --black=<char> --white=<char> --path=<path>`
    where:
    - `<char>` - character to represent black and white pixels
    - `<path>` - path to the BMP image file

...or simply run the `run.sh` script:
    `./run.sh <char> <char> <path>`
    where:
    - `<char>` - character to represent black and white pixels
    - `<path>` - path to the BMP image file
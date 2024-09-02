package edu.school21.printer.app;

import edu.school21.printer.logic.ImageConverter;

public class Program {
    public static void inputCheck(String[] args) {
        if (args.length != 3
                || !args[0].startsWith("--black=")
                || args[0].substring(8).length() != 1
                || !args[1].startsWith("--white=")
                || args[1].substring(8).length() != 1
                || !args[2].startsWith("--path=")) {
            System.out.println(
                    "Usage: java -cp ./target edu.school21.printer.app.Program --black=<char> --white=<char> --path=<path>");
            System.exit(-1);
        }
    }

    public static void main(String[] args) {
        inputCheck(args);
        char black = args[0].charAt(8);
        char white = args[1].charAt(8);
        String path = args[2].substring(7);
        ImageConverter imageConverter = new ImageConverter(black, white, path);
        try {
            imageConverter.convert();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            System.exit(-1);
        }
    }
}

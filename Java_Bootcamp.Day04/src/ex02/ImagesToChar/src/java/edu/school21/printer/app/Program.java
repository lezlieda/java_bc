package edu.school21.printer.app;

import edu.school21.printer.logic.ImageConverter;
import edu.school21.printer.logic.Args;
import com.beust.jcommander.JCommander;

public class Program {

    public static void main(String[] args) {
        Args arguments = new Args();
        try {
            JCommander.newBuilder()
                    .addObject(arguments)
                    .build()
                    .parse(args);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            System.exit(-1);
        }
        String black = arguments.getBlack();
        String white = arguments.getWhite();

        ImageConverter imageConverter = new ImageConverter(black, white,
                "target/resources/it.bmp");
        try {
            imageConverter.convert();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            System.exit(-1);
        }
    }
}

package edu.school21.printer.logic;

import java.awt.Color;
import java.nio.Buffer;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import com.diogonunes.jcdp.color.api.Ansi;
import com.diogonunes.jcdp.color.ColoredPrinter;

public class ImageConverter {
    private final String black;
    private final String white;
    private final String path;

    public ImageConverter(String black, String white, String path) {
        this.black = black;
        this.white = white;
        this.path = path;
    }

    public void convert() throws IOException {
        BufferedImage image = ImageIO.read(new File(path));
        ColoredPrinter printer = new ColoredPrinter.Builder(0, false).build();
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                int color = image.getRGB(j, i);
                if (color == Color.WHITE.getRGB()) {
                    printer.print(" ", Ansi.Attribute.NONE,
                            Ansi.FColor.NONE, Ansi.BColor.valueOf(white));
                } else if (color == Color.BLACK.getRGB()) {
                    printer.print(" ", Ansi.Attribute.NONE,
                            Ansi.FColor.NONE, Ansi.BColor.valueOf(black));
                }
            }
            System.out.println();
        }
    }
}

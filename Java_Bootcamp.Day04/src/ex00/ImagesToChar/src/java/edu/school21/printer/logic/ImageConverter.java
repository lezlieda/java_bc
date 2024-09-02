package edu.school21.printer.logic;

import java.awt.Color;
import java.nio.Buffer;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageConverter {
    private final char black;
    private final char white;
    private final String path;

    public ImageConverter(char black, char white, String path) {
        this.black = black;
        this.white = white;
        this.path = path;
    }

    public void convert() throws IOException {
        BufferedImage image = ImageIO.read(new File(path));
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                int color = image.getRGB(j, i);
                if (color == Color.WHITE.getRGB()) {
                    System.out.print(white);
                } else if (color == Color.BLACK.getRGB()) {
                    System.out.print(black);
                }
            }
            System.out.println();
        }
    }
}
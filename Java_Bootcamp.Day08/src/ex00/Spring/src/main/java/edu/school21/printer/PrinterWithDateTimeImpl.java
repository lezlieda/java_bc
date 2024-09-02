package edu.school21.printer;

import edu.school21.renderer.Renderer;

public class PrinterWithDateTimeImpl implements Printer {
    private final Renderer renderer;

    public PrinterWithDateTimeImpl(Renderer renderer) {
        this.renderer = renderer;
    }

    @Override
    public void print(String message) {
        renderer.renderMessage(message + " " + java.time.LocalDateTime.now());
    }
}

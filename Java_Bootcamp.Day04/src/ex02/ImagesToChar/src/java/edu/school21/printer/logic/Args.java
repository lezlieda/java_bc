package edu.school21.printer.logic;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(separators = "=")
public class Args {
    @Parameter(names = { "--black" }, description = "Black char", required = true)
    private String black;

    @Parameter(names = { "--white" }, description = "White char", required = true)
    private String white;

    public String getBlack() {
        return black;
    }

    public String getWhite() {
        return white;
    }
}

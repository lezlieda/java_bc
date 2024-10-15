package edu.school21.sockets.app;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(separators = "=")
public class Args {
    @Parameter(names = "--server-port", description = "Port to connect", required = true)
    private int port;

    public int getPort() {
        return port;
    }

    public void checkArgs() {
        if (port < 0 || port > 65535) {
            throw new IllegalArgumentException("Port must be in range 0-65535");
        }
    }

}

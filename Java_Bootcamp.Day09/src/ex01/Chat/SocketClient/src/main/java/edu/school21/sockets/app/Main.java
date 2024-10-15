package edu.school21.sockets.app;

import com.beust.jcommander.JCommander;
import edu.school21.sockets.client.Client;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Args arguments = new Args();
        JCommander.newBuilder()
                .addObject(arguments)
                .build()
                .parse(args);
        arguments.checkArgs();
        try {
            Client client = new Client();
            client.start(arguments.getPort());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}

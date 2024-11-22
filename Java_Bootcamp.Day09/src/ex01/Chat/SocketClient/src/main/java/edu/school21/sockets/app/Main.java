package edu.school21.sockets.app;

import com.beust.jcommander.JCommander;
import edu.school21.sockets.client.Client;

public class Main {
    public static void main(String[] args) {
        Args arguments = new Args();
        JCommander.newBuilder().addObject(arguments).build().parse(args);
        arguments.checkArgs();
        Client client = new Client("localhost", arguments.getPort());
        client.run();
    }
}

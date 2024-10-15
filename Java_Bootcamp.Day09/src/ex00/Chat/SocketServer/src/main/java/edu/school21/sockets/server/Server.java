package edu.school21.sockets.server;

import edu.school21.sockets.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

@Component
@Qualifier("server")
public class Server {
    private final UsersService usersService;

    @Autowired
    public Server(@Qualifier("usersService") UsersService usersService) {
        this.usersService = usersService;
    }

    public void start(int port) {
        try {
            ServerSocket server = new ServerSocket(port);
            Socket client = server.accept();
            System.out.print("Connection accepted.");
            DataInputStream in = new DataInputStream(client.getInputStream());
            System.out.println("DataInputStream created");
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            System.out.println("DataOutputStream  created");
            out.writeUTF("Hello from server!");
            out.flush();
            System.out.println("Server sent message with text: Hello from server!");

            while (!client.isClosed()) {
                System.out.println("Server reading from channel");
                String entry = in.readUTF();
                if (entry.equals("signUp")) {
                    signUp(in, out);
                    break;
                } else {
                    out.writeUTF("Unknown command");
                    out.flush();
                }
            }

            System.out.println("Client disconnected");
            System.out.println("Closing connections & channels.");

            client.close();
            server.close();
            in.close();
            out.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void signUp(DataInputStream in, DataOutputStream out) throws IOException {
        out.writeUTF("Enter username:");
        out.flush();
        String username = in.readUTF();
        out.writeUTF("Enter password:");
        out.flush();
        String password = in.readUTF();
        if (usersService.signUp(username, password)) {
            out.writeUTF("Successful!");
            out.flush();
        } else {
            out.writeUTF("User not created");
            out.flush();
        }
    }


}

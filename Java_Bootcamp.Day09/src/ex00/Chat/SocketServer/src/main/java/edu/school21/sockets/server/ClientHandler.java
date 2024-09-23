package edu.school21.sockets.server;

import edu.school21.sockets.services.UsersService;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private final UsersService usersService;
    private final PrintWriter out;
    private final DataInputStream in;


    public ClientHandler(Socket clientSocket, UsersService usersService) {
        this.clientSocket = clientSocket;
        this.usersService = usersService;
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new DataInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        try {
            out.println("Hello from server!");
            String inputLine;
            boolean isExit = false;
            while ((inputLine = in.readUTF()) != null && !isExit) {
                if ("signUp".equals(inputLine)) {
                    isExit = signUp();
                } else {
                    out.println("Unknown command");
                }
            }
            close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean signUp() throws IOException {
        out.println("Enter username:");
        String username = in.readUTF().toString();
        out.println("Enter password:");
        String password = in.readUTF().toString();
        if (usersService.signUp(username, password)) {
            out.println("Successful!");
            return true;
        } else {
            out.println("User not created");
        }
        return false;
    }

    public void close() {
        try {
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

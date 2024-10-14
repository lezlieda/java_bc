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
    private final DataOutputStream out;
    private final DataInputStream in;


    public ClientHandler(Socket clientSocket, UsersService usersService) {
        this.clientSocket = clientSocket;
        this.usersService = usersService;
        try {
            out = new DataOutputStream(clientSocket.getOutputStream());
            in = new DataInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        try {
            out.writeUTF("Hello from server!");
            out.flush();
            String inputLine;
            boolean isExit = false;
            while (!isExit) {
                inputLine = in.readUTF();
                if ("signUp".equals(inputLine)) {
                    isExit = signUp();
                } else {
                    out.writeUTF("Unknown command");
                }
            }
            close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean signUp() throws IOException {
        out.writeUTF("Enter username:");
        out.flush();
        String username = in.readUTF().toString();
        System.out.println(username);
        out.writeUTF("Enter password:");
        out.flush();
        String password = in.readUTF().toString();
        System.out.println(password);
        if (usersService.signUp(username, password)) {
            out.writeUTF("Successful!");
            out.flush();
            return true;
        } else {
            out.writeUTF("User not created");
            out.flush();
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

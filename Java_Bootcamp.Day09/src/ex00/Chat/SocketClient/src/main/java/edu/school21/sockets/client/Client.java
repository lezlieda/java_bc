package edu.school21.sockets.client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private final DataOutputStream out;
    private final DataInputStream in;
    private final BufferedReader br;
    public Client(int port) throws IOException {
        socket = new Socket("localhost", port);
        br = new BufferedReader(new InputStreamReader(System.in));
        out = new DataOutputStream(socket.getOutputStream());
        in = new DataInputStream(socket.getInputStream());
    }

    public void start() throws IOException {
        while (!socket.isOutputShutdown()) {
            if (br.ready()) {
                String clientCommand = br.readLine();
                out.writeUTF(clientCommand);
                out.flush();
                if (clientCommand.equals("signUp")) {
                    signUp();
                }
            }
        }
    }

    public void close() throws IOException {
        br.close();
        in.close();
        out.close();
        socket.close();
    }

    public void signUp() throws IOException {
        System.out.println(in.readUTF());
        String username = br.readLine();
        out.writeUTF(username);
        out.flush();
        System.out.println(in.readUTF());
        String password = br.readLine();
        out.writeUTF(password);
        out.flush();
        System.out.println(in.readUTF());
    }




}

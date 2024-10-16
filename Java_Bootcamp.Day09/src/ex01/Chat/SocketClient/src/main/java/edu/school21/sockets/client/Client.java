package edu.school21.sockets.client;

import java.io.*;
import java.net.Socket;

public class Client {

    public Client() {
    }

    public void start(int port) throws IOException {
        try  {
            Socket socket = new Socket("localhost", port);
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());
            System.out.println(in.readUTF());
            while (!socket.isClosed()) {
                if (br.ready()) {
                    String clientCommand = br.readLine();
                    out.writeUTF(clientCommand);
                    out.flush();
                    String inMessage = in.readUTF();
                    System.out.println(inMessage);
                    if (socket.isClosed())
                        break;
                }
            }
            socket.close();
            br.close();
            out.close();
            in.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}

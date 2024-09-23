package edu.school21.sockets.server;

import edu.school21.sockets.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@Qualifier("server")
public class Server {
    private final UsersService usersService;

   @Autowired
    public Server(@Qualifier("usersService") UsersService usersService) {
        this.usersService = usersService;
    }

    public void start(int port) {
        ExecutorService executorService = Executors.newFixedThreadPool(128);
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                executorService.submit(new ClientHandler(clientSocket, usersService));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}

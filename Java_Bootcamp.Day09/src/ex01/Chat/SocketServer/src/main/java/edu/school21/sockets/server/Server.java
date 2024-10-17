package edu.school21.sockets.server;

import edu.school21.sockets.repositories.MessagesRepository;
import edu.school21.sockets.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@Qualifier("server")
public class Server {
    private final UsersService usersService;
    private final MessagesRepository messagesRepository;
    static ExecutorService executorService = Executors.newFixedThreadPool(10);

    @Autowired
    public Server(@Qualifier("usersService") UsersService usersService, @Qualifier("messagesRepositoryImpl") MessagesRepository messagesRepository) {
        this.usersService = usersService;
        this.messagesRepository = messagesRepository;
    }

    public void start(int port) {
        try {
            ServerSocket server = new ServerSocket(port);
            while (!server.isClosed()) {
                Socket client = server.accept();
                executorService.execute(new ClientHandler(client, usersService, messagesRepository));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}

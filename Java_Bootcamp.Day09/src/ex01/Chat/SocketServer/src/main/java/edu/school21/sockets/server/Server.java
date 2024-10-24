package edu.school21.sockets.server;

import edu.school21.sockets.models.Message;
import edu.school21.sockets.repositories.MessagesRepository;
import edu.school21.sockets.services.UsersService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;


@Component
@Qualifier("server")
public class Server {
    private final UsersService usersService;
    private final MessagesRepository messagesRepository;
    private final Map<SocketAddress, SocketChannel> clients = new HashMap<>();
    private final Queue<SocketAddress> connectedClientsEvents = new ConcurrentLinkedQueue<>();
    private final Queue<SocketAddress> disconnectedClientsEvents = new ConcurrentLinkedQueue<>();
    private final Queue<Message> messages = new ArrayBlockingQueue<>(1000);
    private volatile boolean isRunning = true;
    private static final int TIME_OUT = 1000;

    public Server(UsersService usersService, MessagesRepository messagesRepository) {
        this.usersService = usersService;
        this.messagesRepository = messagesRepository;
    }

    public void start(int port, InetAddress address) {
        try {
            try {
                ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
                serverSocketChannel.configureBlocking(false);
                ServerSocket serverSocket = serverSocketChannel.socket();
                serverSocket.bind(new InetSocketAddress(address, port));
                try (Selector selector = Selector.open()) {
                    serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
                    System.out.println("Server started on address: " + address + " and port: " + port);
                    while (isRunning) {
                        handleSelector(selector);
                    }
                    System.out.println("Server stopped on address: " + address + " and port: " + port);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);

        }
    }


    private void handleSelector(Selector selector) {
        try {
            selector.select(performIO(selector.keys()), TIME_OUT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void performIO(SelectionKey key) {
        if (key.isAcceptable()) {
            acceptConnection(key);
        }
    }

    private void acceptConnection(SelectionKey key) {
        try {
            ServerSocketChannel server = (ServerSocketChannel) key.channel();
            SocketChannel client = server.accept();
            client.configureBlocking(false);
            client.register(key.selector(), SelectionKey.OP_READ);
            clients.put(client.getRemoteAddress(), client);
            connectedClientsEvents.add(client.getRemoteAddress());
            send(client.getRemoteAddress(), "Hello from server!");
            System.out.println("Client connected: " + client.getRemoteAddress());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void send(SocketAddress address, String message) {
        try {
            SocketChannel client = clients.get(address);
            client.write(ByteBuffer.wrap(message.getBytes()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}

//package edu.school21.sockets.server;
//
//import edu.school21.sockets.models.Message;
//import edu.school21.sockets.models.User;
//import edu.school21.sockets.repositories.MessagesRepository;
//import edu.school21.sockets.services.UsersService;
//
//import java.io.DataInputStream;
//import java.io.DataOutputStream;
//import java.io.IOException;
//import java.net.Socket;
//
//public class ClientHandler implements Runnable {
//    private static Socket client;
//    private final UsersService usersService;
//    private final MessagesRepository messagesRepository;
//
//    public ClientHandler(Socket client, UsersService usersService, MessagesRepository messagesRepository) {
//        ClientHandler.client = client;
//        this.usersService = usersService;
//        this.messagesRepository = messagesRepository;
//    }
//
//    @Override
//    public void run() {
//        try {
//            DataInputStream in = new DataInputStream(client.getInputStream());
//            DataOutputStream out = new DataOutputStream(client.getOutputStream());
//            out.writeUTF("Hello from server!");
//            out.flush();
//            while (!client.isClosed()) {
//                String entry = in.readUTF();
//                switch (entry) {
//                    case "signUp":
//                        signUp(in, out);
//                        break;
//                    case "signIn":
//                        User user = signIn(in, out);
//                        if (user != null) {
//                            chat(in, out, user);
//                        } else {
//                            out.writeUTF("User not found");
//                            out.flush();
//                        }
//
//                        break;
//                    case "exit":
//                        out.writeUTF("Goodbye!");
//                        out.flush();
//                        client.close();
//                        break;
//                    default:
//                        out.writeUTF("Unknown command");
//                        out.flush();
//                        break;
//                }
//            }
//            in.close();
//            out.close();
//            client.close();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    private User signIn(DataInputStream in, DataOutputStream out) throws IOException {
//        out.writeUTF("Enter username:");
//        out.flush();
//        String username = in.readUTF();
//        out.writeUTF("Enter password:");
//        out.flush();
//        String password = in.readUTF();
//        return usersService.signIn(username, password);
//    }
//
//    public void signUp(DataInputStream in, DataOutputStream out) throws IOException {
//        out.writeUTF("Enter username:");
//        out.flush();
//        String username = in.readUTF();
//        out.writeUTF("Enter password:");
//        out.flush();
//        String password = in.readUTF();
//        if (usersService.signUp(username, password)) {
//            out.writeUTF("Successful!");
//            out.flush();
//        } else {
//            out.writeUTF("User not created");
//            out.flush();
//        }
//    }
//
//    public void chat(DataInputStream in, DataOutputStream out, User user) throws IOException {
//        out.writeUTF("Start messaging");
//        out.flush();
//        while (!client.isClosed()) {
//            String entry = in.readUTF();
//            if (entry.equals("exit")) {
//                out.writeUTF("Goodbye!");
//                out.flush();
//                client.close();
//                break;
//            }
//            Message message = new Message(user, entry);
//            messagesRepository.save(message);
//            out.writeUTF(user.getUsername() + ": " + entry);
//            out.flush();
//        }
//
//    }
//}

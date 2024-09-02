package edu.school21.chat.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;
import edu.school21.chat.repositories.MessagesRepository;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Program {
    public static void main(String[] args) {
        HikariDataSource dataSource = getDataSource();
        User creator = new User(1L, "user1", "password1", new ArrayList<>(), new ArrayList<>());
        User author = creator;
        Chatroom chatroom = new Chatroom(3L, "chatroom3", creator, new ArrayList<>());
        Message message = new Message(null, author, chatroom, "wuzzzuuuuup!", LocalDateTime.now());
        MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl(dataSource);

        System.out.println("\t--==ADD A MESSAGE==--");
        messagesRepository.save(message);
        System.out.println(message.getId());
//
//        System.out.println("\t--==ADD A MESSAGE WITH AUTHOR'S WRONG ID ==--");
//        author = new User(345345L, "wrongID", "wrongID", null, null);
//        message = new Message(null, author, chatroom, "wuzzzuuuuup!", LocalDateTime.now());
//        messagesRepository.save(message);
//        System.out.println(message.getId());

//        System.out.println("\t--==ADD A MESSAGE WITH AUTHOR'S NULL ID ==--");
//        author = new User(null, "wrongID", "wrongID", null, null);
//        message = new Message(null, author, chatroom, "wuzzzuuuuup!", LocalDateTime.now());
//        messagesRepository.save(message);
//        System.out.println(message.getId());

//        System.out.println("\t--==ADD A MESSAGE WITH CHATROOMS'S WRONG ID ==--");
//        author = creator;
//        chatroom = new Chatroom(312L, "chatroom3", creator, new ArrayList<>());
//        message = new Message(null, author, chatroom, "wuzzzuuuuup!", LocalDateTime.now());
//        messagesRepository.save(message);
//        System.out.println(message.getId());

//        System.out.println("\t--==ADD A MESSAGE WITH CHATROOMS'S NULL ID ==--");
//        chatroom = new Chatroom(null, "chatroom3", creator, new ArrayList<>());
//        message = new Message(null, author, chatroom, "wuzzzuuuuup!", LocalDateTime.now());
//        messagesRepository.save(message);
//        System.out.println(message.getId());


    }

    private static HikariDataSource getDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/");
        config.setUsername("lezlieda");
        config.setPassword("lezlieda");
        return new HikariDataSource(config);
    }
}

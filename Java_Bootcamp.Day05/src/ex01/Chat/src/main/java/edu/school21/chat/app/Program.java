package edu.school21.chat.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.models.Message;
import edu.school21.chat.repositories.MessagesRepository;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;

import java.util.Optional;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        System.out.println("Enter a message ID");
        Scanner scanner = new Scanner(System.in);
        long id = scanner.nextLong();
        MessagesRepository mr = new MessagesRepositoryJdbcImpl(getDataSource());
        try {
            Optional<Message> messageOptional = mr.findById(id);
            if (messageOptional.isPresent()) {
                Message message = messageOptional.get();
                System.out.println(message);
            } else {
                System.out.println("Message not found!");
            }
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
    }

    private static HikariDataSource getDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/");
        config.setUsername("postgres");
        config.setPassword("qwe");
        return new HikariDataSource(config);
    }

}

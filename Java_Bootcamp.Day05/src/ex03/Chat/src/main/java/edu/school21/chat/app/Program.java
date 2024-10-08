package edu.school21.chat.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.models.Message;
import edu.school21.chat.repositories.MessagesRepository;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;

import java.time.LocalDateTime;
import java.util.Optional;

public class Program {
    public static void main(String[] args) {
        MessagesRepository mr = new MessagesRepositoryJdbcImpl(getDataSource());
        Optional<Message> messageOptional = mr.findById(2L);
        if (messageOptional.isPresent()) {
            Message message = messageOptional.get();
            message.setText("Lorem ipsum dolor sit amet");
            message.setDateTime(LocalDateTime.now());
            mr.update(message);
        }
        messageOptional = mr.findById(3L);
        if (messageOptional.isPresent()) {
            Message message = messageOptional.get();
            message.setDateTime(LocalDateTime.now());
            message.setAuthor(null);
            mr.update(message);
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

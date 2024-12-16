package edu.school21.sockets.repositories;

import edu.school21.sockets.config.TestApplicationConfig;
import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class MessagesRepositoryImplTest {
    private MessagesRepository messagesRepository;

    @BeforeEach
    void setUp() {
        ApplicationContext context = new AnnotationConfigApplicationContext(TestApplicationConfig.class);
        messagesRepository = new MessagesRepositoryImpl(context.getBean("testDatabase", DataSource.class));
    }

    @ParameterizedTest(name = "findByIdSuccess")
    @CsvSource({"0,Hello World!", "1,Hello User!", "2,Hello Guest!", "3,Hello Root!", "4,Hello Superuser!"})
    void findById(long id, String text) {
        assertNotNull(messagesRepository.findById(id));
        assertEquals(text, messagesRepository.findById(id).get().getText());
    }

    @ParameterizedTest(name = "findByIdFail")
    @ValueSource(longs = {5, 6, 7, 8, 9})
    void findByIdFail(long id) {
        assertFalse(messagesRepository.findById(id).isPresent());
    }

    @Test
    void save() {
        User user = new User(0L, "admin", "admin");
        Message message = new Message(null, user, "Hello New Message!", LocalDateTime.now());
        assertEquals(5, messagesRepository.save(message));
        assertNotNull(messagesRepository.findById(5L));
        assertEquals("Hello New Message!", messagesRepository.findById(5L).get().getText());
    }

    @ParameterizedTest(name = "findAll")
    @CsvSource({"0,Hello World!", "1,Hello User!", "2,Hello Guest!", "3,Hello Root!", "4,Hello Superuser!"})
    void findAll(long id, String text) {
        assertEquals(5, messagesRepository.findAll().size());
        assertEquals(text, messagesRepository.findAll().get((int) id).getText());
    }

}
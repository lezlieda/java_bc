package edu.school21.sockets.repositories;

import edu.school21.sockets.models.Chatroom;
import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class MessagesRepositoryImplTest {
    private EmbeddedDatabase database;
    private MessagesRepository messagesRepository;

    @BeforeEach
    void setUp() {
        database = new EmbeddedDatabaseBuilder()
                .generateUniqueName(true)
                .addScript("schema.sql")
                .addScript("data.sql")
                .build();
        messagesRepository = new MessagesRepositoryImpl(database);
    }

    @AfterEach
    void tearDown() {
        database.shutdown();
    }

    @ParameterizedTest(name = "findByIdSuccess")
    @CsvSource({"0,Hello World!,user,room2", "1,Hello User!,guest,room2", "2,Wuzup?,user,room2",
            "3,Not really,guest,room2", "4,ORLY?,user,room2"})
    void findById(long id, String text, String author, String chatName) {
        assertNotNull(messagesRepository.findById(id));
        assertEquals(text, messagesRepository.findById(id).get().getText());
        assertEquals(author, messagesRepository.findById(id).get().getAuthor().getUsername());
        assertEquals(chatName, messagesRepository.findById(id).get().getChatroom().getName());
    }


    @ParameterizedTest(name = "findByIdFail")
    @ValueSource(longs = {5, 6, 7, 8, 9})
    void findByIdFail(long id) {
        assertFalse(messagesRepository.findById(id).isPresent());
    }

    @Test
    void save() {
        User user = new User(1L, "user", "user");
        Chatroom chatroom = new Chatroom(4L, "room5", user, null);
        Message message = new Message(null, user, chatroom, "Hello New Message!", LocalDateTime.now());
        messagesRepository.save(message);
        assertNotNull(messagesRepository.findById(5L));
        assertEquals("Hello New Message!", messagesRepository.findById(5L).get().getText());
        assertEquals(user.getUsername(), messagesRepository.findById(5L).get().getAuthor().getUsername());
    }

    @ParameterizedTest(name = "findAll")
    @CsvSource({"0,Hello World!", "1,Hello User!", "2,Wuzup?", "3,Not really", "4,ORLY?"})
    void findAll(long id, String text) {
        assertEquals(5, messagesRepository.findAll().size());
        assertEquals(text, messagesRepository.findAll().get((int) id).getText());
    }

    @Test
    void getLast30Messages() {
        assertEquals(5, messagesRepository.getLast30Messages(1L).size());
        assertEquals("Hello World!", messagesRepository.getLast30Messages(1L).get(0).getText());
        assertEquals("Hello User!", messagesRepository.getLast30Messages(1L).get(1).getText());
        assertEquals("Wuzup?", messagesRepository.getLast30Messages(1L).get(2).getText());
        assertEquals("Not really", messagesRepository.getLast30Messages(1L).get(3).getText());
        assertEquals("ORLY?", messagesRepository.getLast30Messages(1L).get(4).getText());
    }

}
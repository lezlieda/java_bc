package edu.school21.sockets.services;

import edu.school21.sockets.models.Chatroom;
import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.MessagesRepository;
import edu.school21.sockets.repositories.MessagesRepositoryImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MessagesServiceTest {
    private EmbeddedDatabase db;
    private MessagesRepository messagesRepository;

    @BeforeEach
    void setUp() {
        db = new EmbeddedDatabaseBuilder()
                .generateUniqueName(true)
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("schema.sql")
                .addScript("data.sql")
                .build();
        messagesRepository = new MessagesRepositoryImpl(db);
    }

    @AfterEach
    void tearDown() {
        db.shutdown();
    }

    @Test
    void checkConnection() {
        try {
            assertNotNull(db.getConnection());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @ParameterizedTest(name = "getLast30MessagesEmptyChatroom")
    @ValueSource(longs = {100, 200, 300})
        // Provide chatroom IDs that do not exist in the database
    void getLast30MessagesEmptyChatroom(long chatroomId) {
        MessagesService messagesService = new MessagesServiceImpl(messagesRepository);
        List<Message> messages = messagesService.getLast30Messages(chatroomId);
        assertTrue(messages.isEmpty());
    }

    @ParameterizedTest(name = "getLast30MessagesNonExistentChatroom")
    @ValueSource(longs = {100, 200, 300})
        // Provide chatroom IDs that do not exist in the database
    void getLast30MessagesNonExistentChatroom(long chatroomId) {
        MessagesService messagesService = new MessagesServiceImpl(messagesRepository);
        List<Message> messages = messagesService.getLast30Messages(chatroomId);
        assertTrue(messages.isEmpty());
    }


    @Test
    void testSendMessageSuccess() {
        MessagesService messagesService = new MessagesServiceImpl(messagesRepository);
        User user = new User(4L, "test", "test");
        Chatroom chatroom = new Chatroom(0L, "Test chatroom", user, null);
        Message message = new Message(null, user, chatroom, "Test message", null);
        assertTrue(messagesService.saveMessage(message));
    }

}
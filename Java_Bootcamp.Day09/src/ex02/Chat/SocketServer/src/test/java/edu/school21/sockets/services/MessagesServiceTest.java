package edu.school21.sockets.services;

import edu.school21.sockets.config.TestApplicationConfig;
import edu.school21.sockets.models.Chatroom;
import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.MessagesRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MessagesServiceTest {
    private MessagesService messagesService;

    @BeforeEach
    void setUp() {
        ApplicationContext context = new AnnotationConfigApplicationContext(TestApplicationConfig.class);
        messagesService = new MessagesServiceImpl(new MessagesRepositoryImpl(context.getBean("testDatabase", DataSource.class)));
    }

    @ParameterizedTest(name = "getLast30MessagesEmptyChatroom")
    @ValueSource(longs = {100, 200, 300})
    void getLast30MessagesEmptyChatroom(long chatroomId) {
        List<Message> messages = messagesService.getLast30Messages(chatroomId);
        assertTrue(messages.isEmpty());
    }

    @Test
    void testSendMessageSuccess() {
        User user = new User(4L, "test", "test");
        Chatroom chatroom = new Chatroom(0L, "Test chatroom", user, null);
        Message message = new Message(null, user, chatroom, "Test message", null);
        assertTrue(messagesService.saveMessage(message));
    }

}
package edu.school21.sockets.services;

import edu.school21.sockets.config.TestApplicationConfig;
import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.MessagesRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;

class MessagesServiceTest {
    private MessagesService messagesService;

    @BeforeEach
    void setUp() {
        ApplicationContext context = new AnnotationConfigApplicationContext(TestApplicationConfig.class);
        messagesService = new MessagesServiceImpl(new MessagesRepositoryImpl(context.getBean("testDatabase", DataSource.class)));
    }


    @ParameterizedTest(name = "testSendMessageSuccess")
    @ValueSource(strings = {"message1", "message2", "message3"})
    void testSendMessageSuccess(String message) {
        assertTrue(messagesService.saveMessage(new Message(new User(), message)));
    }

}
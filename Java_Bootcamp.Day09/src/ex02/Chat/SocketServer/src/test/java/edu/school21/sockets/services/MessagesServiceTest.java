package edu.school21.sockets.services;

import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.MessagesRepository;
import edu.school21.sockets.repositories.MessagesRepositoryImpl;
import edu.school21.sockets.repositories.UsersRepository;
import edu.school21.sockets.repositories.UsersRepositoryImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import static org.junit.jupiter.api.Assertions.*;

class MessagesServiceTest {
    private EmbeddedDatabase db;
    private MessagesRepository messagesRepository;
    private UsersRepository usersRepository;

    @BeforeEach
    void setUp() {
        db = new EmbeddedDatabaseBuilder()
                .generateUniqueName(true)
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("schema.sql")
                .addScript("data.sql")
                .build();
        usersRepository = new UsersRepositoryImpl(db);
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

    @ParameterizedTest(name = "testSendMessageSuccess")
    @CsvSource({"admin,admin message", "user,user message"})
    void testSendMessageSuccess(String username, String message) {
        MessagesService messagesService = new MessagesServiceImpl(messagesRepository);
        assertTrue(messagesService.saveMessage(new Message(new User(), message)));
    }

}
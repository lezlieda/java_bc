package edu.school21.sockets.services;

import edu.school21.sockets.repositories.ChatroomsRepositoryImpl;
import edu.school21.sockets.repositories.MessagesRepositoryImpl;
import edu.school21.sockets.repositories.UsersRepositoryImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import static org.junit.jupiter.api.Assertions.*;

class ChatroomsServiceImplTest {
    private EmbeddedDatabase db;
    private ChatroomsService chatroomsService;

    @BeforeEach
    void setUp() {
        db = new EmbeddedDatabaseBuilder()
                .generateUniqueName(true)
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("schema.sql")
                .addScript("data.sql")
                .build();
        chatroomsService = new ChatroomsServiceImpl(new ChatroomsRepositoryImpl(db));
    }

    @Test
    void checkConnection() {
        try {
            assertNotNull(db.getConnection());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    void tearDown() {
        db.shutdown();
    }
    @Test
    void createChatroom() {
    }

    @Test
    void getChatroomById() {
    }

    @Test
    void getAllChatrooms() {
        System.out.println(chatroomsService.getChatroomList());
    }
}
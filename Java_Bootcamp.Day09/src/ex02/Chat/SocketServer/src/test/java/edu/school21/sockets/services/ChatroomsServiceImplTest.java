package edu.school21.sockets.services;

import edu.school21.sockets.config.TestApplicationConfig;
import edu.school21.sockets.models.Chatroom;
import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.ChatroomsRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChatroomsServiceImplTest {
    private ChatroomsService chatroomsService;

    @BeforeEach
    void setUp() {
        ApplicationContext context = new AnnotationConfigApplicationContext(TestApplicationConfig.class);
        chatroomsService = new ChatroomsServiceImpl(new ChatroomsRepositoryImpl(context.getBean("testDatabase", DataSource.class)));
    }

    @Test
    void createChatroomSuccess() {
        User user = new User(0L, "admin", "admin");
        assertTrue(chatroomsService.createChatroom("test", user));
    }


    @Test
    void getChatroomByIdSuccess() {
        Chatroom chatroom = new Chatroom(0L, "room1", new User(1L, "user", "user"), null);
        assertEquals(chatroom, chatroomsService.getChatroomById(0L));
    }

    @Test
    void getChatroomByIdFail() {
        assertNull(chatroomsService.getChatroomById(100L));
    }

    @Test
    void getAllChatroomsWithMultipleRooms() {
        List<Chatroom> chatrooms = chatroomsService.getAllChatrooms();
        assertNotNull(chatrooms);
        assertTrue(chatrooms.size() > 1);
        assertEquals("room1", chatrooms.get(0).getName());
        assertEquals("room2", chatrooms.get(1).getName());
        assertEquals("user", chatrooms.get(0).getOwner().getUsername());
        assertEquals("user", chatrooms.get(1).getOwner().getUsername());
    }


}
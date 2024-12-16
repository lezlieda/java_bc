package edu.school21.sockets.repositories;

import edu.school21.sockets.config.TestApplicationConfig;
import edu.school21.sockets.models.Chatroom;
import edu.school21.sockets.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChatroomsRepositoryImplTest {
    private ChatroomsRepository chatroomsRepository;
    private UsersRepository usersRepository;

    @BeforeEach
    void setUp() {
        ApplicationContext context = new AnnotationConfigApplicationContext(TestApplicationConfig.class);
        chatroomsRepository = new ChatroomsRepositoryImpl(context.getBean("testDatabase", DataSource.class));
        usersRepository = new UsersRepositoryImpl(context.getBean("testDatabase", DataSource.class));
    }

    @ParameterizedTest
    @CsvSource({"0,1,room1", "1,1,room2", "2,1,room3", "3,3,room4", "4,3,room5"})
    void findById(long id, long userId, String name) {
        assertNotNull(chatroomsRepository.findById(id));
        assertEquals(userId, chatroomsRepository.findById(id).get().getOwner().getId());
        assertEquals(name, chatroomsRepository.findById(id).get().getName());
    }

    @Test
    void findAll() {
        List<Chatroom> rooms = chatroomsRepository.findAll();
        assertEquals(5, rooms.size());
        assertEquals(1, rooms.get(0).getOwner().getId());
        assertEquals("room1", rooms.get(0).getName());
        assertEquals(1, rooms.get(1).getOwner().getId());
        assertEquals("room2", rooms.get(1).getName());
        assertEquals(1, rooms.get(2).getOwner().getId());
        assertEquals("room3", rooms.get(2).getName());
        assertEquals(3, rooms.get(3).getOwner().getId());
        assertEquals("room4", rooms.get(3).getName());
        assertEquals(3, rooms.get(4).getOwner().getId());
        assertEquals("room5", rooms.get(4).getName());
    }

    @Test
    void save() {
        User test = new User();
        test.setUsername("test");
        test.setPassword("test");
        usersRepository.save(test);
        Chatroom chat = new Chatroom(null, "new room", test, null);
        long id = chatroomsRepository.save(chat);
        assertEquals(5, id);
    }

    @Test
    void delete() {
        chatroomsRepository.delete(1L);
        assertFalse(chatroomsRepository.findById(1L).isPresent());
    }


    @Test
    void update() {
        Chatroom chatroom = chatroomsRepository.findById(0L).get();
        chatroom.setName("updated room");
        chatroomsRepository.update(chatroom);
        assertEquals("updated room", chatroomsRepository.findById(0L).get().getName());
    }
}
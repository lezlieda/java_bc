package edu.school21.sockets.repositories;

import edu.school21.sockets.models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UsersRepositoryImplTest {
    private EmbeddedDatabase database;
    private UsersRepository usersRepository;


    @BeforeEach
    void setUp() {
        database = new EmbeddedDatabaseBuilder()
                .generateUniqueName(true)
                .addScript("schema.sql")
                .addScript("data.sql")
                .build();
        usersRepository = new UsersRepositoryImpl(database);
    }

    @AfterEach
    void tearDown() {
        database.shutdown();
    }

    @ParameterizedTest(name = "findByIdSuccess")
    @CsvSource({"0,admin", "1,user", "2,guest", "3,root", "4,superuser"})
    void findByIdSuccess(long id, String username) {
        assertNotNull(usersRepository.findById(id));
        assertEquals(username, usersRepository.findById(id).get().getUsername());
    }

    @ParameterizedTest(name = "findByIdFail")
    @ValueSource(longs = {5, 6, 7, 8, 9})
    void findByIdFail(long id) {
        assertFalse(usersRepository.findById(id).isPresent());
    }

    @Test
    void findAll() {
        List<User> users = usersRepository.findAll();
        assertEquals(5, users.size());
        assertEquals("admin", users.get(0).getUsername());
        assertEquals("user", users.get(1).getUsername());
        assertEquals("guest", users.get(2).getUsername());
        assertEquals("root", users.get(3).getUsername());
        assertEquals("superuser", users.get(4).getUsername());
    }

    @Test
    void save() {
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");
        long id = usersRepository.save(user);
        assertEquals(5, id);
        assertEquals("test", usersRepository.findById(id).get().getUsername());
        assertEquals("test", usersRepository.findById(id).get().getPassword());
    }

    @Test
    void update() {
        User user = usersRepository.findById(0L).get();
        user.setUsername("pwned");
        user.setPassword("pwned");
        usersRepository.update(user);
        assertEquals("pwned", usersRepository.findById(0L).get().getUsername());
        assertEquals("pwned", usersRepository.findById(0L).get().getPassword());
    }

    @Test
    void delete() {
        usersRepository.delete(0L);
        assertFalse(usersRepository.findById(0L).isPresent());
    }

    @Test
    void findByUsername() {
        Optional<User> user = usersRepository.findByUsername("admin");
        assertTrue(user.isPresent());
        assertEquals("admin", user.get().getUsername());
        assertEquals("admin", user.get().getPassword());
        assertEquals(0, user.get().getId());
    }
}
package edu.school21.spring.service.services;

import edu.school21.spring.service.repositories.UsersRepository;
import edu.school21.spring.service.repositories.UsersRepositoryJdbcImpl;
import edu.school21.spring.service.repositories.UsersRepositoryJdbcTemplateImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import edu.school21.spring.service.models.User;

import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UsersServiceTest {
    private EmbeddedDatabase db;
    private UsersRepository usersRepository;


    @BeforeEach
    void init() {
        db = new EmbeddedDatabaseBuilder()
                .generateUniqueName(true)
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("schema.sql")
                .addScript("data.sql")
                .build();
        usersRepository = new UsersRepositoryJdbcImpl(db);
    }

    @AfterEach
    void close() {
        db.shutdown();
    }

    @Test
    void checkConnection() {
        try {
            assertNotNull(db.getConnection());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @ParameterizedTest(name = "testSignUpSuccess")
    @ValueSource(strings = {"test1@student.school21.ru", "test2@student.school21.ru", "test3@student.school21.ru"})
    void testSignUpSuccess(String email) {
        UsersService usersService = new UsersServiceImpl(usersRepository);
        String psd = usersService.signUp(email);
        Optional<User> test = usersRepository.findByEmail(email);
        assertTrue(test.isPresent());
        assertEquals(psd, test.get().getPassword());
        assertEquals(email, test.get().getEmail());
    }

    @ParameterizedTest(name = "testSignUpFail")
            @ValueSource(strings = {"lezlieda@student.school21.ru", "lifeboak@student.school21.ru", "richesel@student.school21.ru"})
    void testSignUpFail(String email) {
        UsersService usersService = new UsersServiceImpl(usersRepository);
        assertThrows(UserAlreadyExistsException.class, () -> usersService.signUp(email));
    }

}




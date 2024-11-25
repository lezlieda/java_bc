package edu.school21.sockets.services;

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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UsersServiceTest {
    private EmbeddedDatabase db;
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

    @ParameterizedTest(name = "testSignUpSuccess")
    @CsvSource({"test1,password1", "test2,password2", "test3,password3"})
    void testSignUpSuccess(String login, String password) {
        UsersService usersService = new UsersServiceImpl(usersRepository);
        assertTrue(usersService.signUp(login, password));
        assertNotNull(usersRepository.findByUsername(login));
    }

    @ParameterizedTest(name = "testSignUpFail")
    @CsvSource({"admin,admin", "user,user", "guest,guest"})
    void testSignUpFail(String login, String password) {
        UsersService usersService = new UsersServiceImpl(usersRepository);
        assertFalse(usersService.signUp(login, password));
    }

    @Test
    void testDbConf() {
        try {
            String SQL_FIND_ALL = "SELECT * FROM \"users\"";
            Connection connection = db.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL);
            assertNotNull(statement.executeQuery());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

package edu.school21.sockets.services;

import edu.school21.sockets.config.TestApplicationConfig;
import edu.school21.sockets.repositories.UsersRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;

class UsersServiceTest {
    private UsersService usersService;

    @BeforeEach
    void init() {
        ApplicationContext context = new AnnotationConfigApplicationContext(TestApplicationConfig.class);
        usersService = new UsersServiceImpl(new UsersRepositoryImpl(context.getBean("testDatabase",
                DataSource.class)), context.getBean("bCryptPasswordEncoder", BCryptPasswordEncoder.class));
    }

    @ParameterizedTest(name = "testSignUpSuccess")
    @CsvSource({"test1,password1", "test2,password2", "test3,password3"})
    void testSignUpSuccess(String login, String password) {
        assertTrue(usersService.signUp(login, password));
    }

    @ParameterizedTest(name = "testSignUpFail")
    @CsvSource({"admin,admin", "user,user", "guest,guest"})
    void testSignUpFail(String login, String password) {
        assertFalse(usersService.signUp(login, password));
    }
}

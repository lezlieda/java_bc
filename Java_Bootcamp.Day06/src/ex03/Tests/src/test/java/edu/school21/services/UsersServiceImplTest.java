package edu.school21.services;

import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.exceptions.EntityNotFoundException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class UsersServiceImplTest {
    private final String CORRECT_LOGIN = "abc";
    private final String INCORRECT_LOGIN = "dea";
    private final String CORRECT_PASSWORD = "qwerty";
    private final String INCORRECT_PASSWORD = "thankyou";
    private final User testUser1 = new User(34L, CORRECT_LOGIN, CORRECT_PASSWORD, false);
    private final User testUser2 = new User(334L, CORRECT_LOGIN, CORRECT_PASSWORD, true);

    @Mock
    UsersRepository mockedUsersRepository;
    UsersServiceImpl mockedUsersServiceImpl;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        mockedUsersServiceImpl = new UsersServiceImpl(mockedUsersRepository);
    }


    @Test
    void testCorrectLogin() {
        Mockito.when(mockedUsersRepository.findByLogin(CORRECT_LOGIN)).thenReturn(testUser1);
        assertTrue(mockedUsersServiceImpl.authenticate(CORRECT_LOGIN, CORRECT_PASSWORD));
        assertTrue(testUser1.getStatus());
        Mockito.verify(mockedUsersRepository).update(testUser1);
    }

    @Test
    void testIncorrectLogin() {
        Mockito.when(mockedUsersRepository.findByLogin(INCORRECT_LOGIN)).thenThrow(EntityNotFoundException.class);
        assertThrows(EntityNotFoundException.class, () -> mockedUsersServiceImpl.authenticate(INCORRECT_LOGIN, CORRECT_PASSWORD));
        assertFalse(testUser1.getStatus());
    }

    @Test
    void testIncorrectPassword() {
        Mockito.when(mockedUsersRepository.findByLogin(CORRECT_LOGIN)).thenReturn(testUser1);
        assertFalse(mockedUsersServiceImpl.authenticate(CORRECT_LOGIN, INCORRECT_PASSWORD));
        assertFalse(testUser1.getStatus());
    }

    @Test
    void testAlreadyLoggenIn() {
        Mockito.when(mockedUsersRepository.findByLogin(CORRECT_LOGIN)).thenReturn(testUser2);
        assertThrows(AlreadyAuthenticatedException.class, () -> mockedUsersServiceImpl.authenticate(CORRECT_LOGIN, CORRECT_PASSWORD));
    }
}
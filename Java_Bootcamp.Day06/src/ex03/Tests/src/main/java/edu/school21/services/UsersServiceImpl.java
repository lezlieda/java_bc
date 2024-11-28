package edu.school21.services;

import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.exceptions.EntityNotFoundException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;

public class UsersServiceImpl {
    private final UsersRepository usersRepository;

    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public boolean authenticate(String login, String password) throws AlreadyAuthenticatedException, EntityNotFoundException {
        if (usersRepository.findByLogin(login).getStatus())
            throw new AlreadyAuthenticatedException("User: " + login + ", already logged in!");
        User user = usersRepository.findByLogin(login);
        if (user.getPassword().equals(password)) {
            user.setStatus(true);
            usersRepository.update(user);
            return true;
        }
        return false;
    }
}

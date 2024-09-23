package edu.school21.sockets.services;

import edu.school21.sockets.repositories.UsersRepository;

public interface UsersService {
       boolean signUp(String login, String password);
}

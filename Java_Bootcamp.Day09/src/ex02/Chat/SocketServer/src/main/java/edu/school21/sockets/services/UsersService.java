package edu.school21.sockets.services;

import edu.school21.sockets.models.User;

public interface UsersService {
       boolean signUp(String login, String password);
       User signIn(String login, String password);
}

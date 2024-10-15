package edu.school21.sockets.services;

import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Qualifier("usersService")
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public boolean signUp(String login, String password) {
        if (usersRepository.findByUsername(login).isPresent()) {
            return false;
        }
        User user = new User();
        user.setUsername(login);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        return usersRepository.save(user) > -1;
    }
}

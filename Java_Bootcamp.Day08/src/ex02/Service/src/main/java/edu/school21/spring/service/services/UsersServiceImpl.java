package edu.school21.spring.service.services;

import edu.school21.spring.service.models.User;
import edu.school21.spring.service.repositories.UsersRepository;

public class UsersServiceImpl implements UsersService {
    private final UsersRepository usersRepository;

    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }
    @Override
    public String signUp(String email) {
        if (usersRepository.findByEmail(email).isPresent()) {
            throw new UserAlreadyExistsException("User " + email + " already exists!");
        }
        Long id = usersRepository.findAll().size() + 1L;
        String password = generatePassword();
        usersRepository.save(new User(id, email, password));
        return password;
    }

    private String generatePassword() {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            int index = (int) (Math.random() * chars.length());
            password.append(chars.charAt(index));
        }
        return password.toString();
    }
}

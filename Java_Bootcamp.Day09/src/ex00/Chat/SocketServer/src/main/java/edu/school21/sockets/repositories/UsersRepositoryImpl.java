package edu.school21.sockets.repositories;

import edu.school21.sockets.models.User;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;

public class UsersRepositoryImpl implements UsersRepository {
    JdbcTemplate jdbcTemplate;
    private final String SQL_FIND_BY_ID = "SELECT * FROM users WHERE id = ?";
    private final String SQL_FIND_BY_USERNAME = "SELECT * FROM users WHERE username = ?";
    private final String SQL_FIND_ALL = "SELECT * FROM users";
    private final String SQL_SAVE = "INSERT INTO users (email) VALUES (?)";
    private final String SQL_UPDATE = "UPDATE users SET username = ?, password = ? WHERE id = ?";
    private final String SQL_DELETE = "DELETE FROM users WHERE id = ?";
    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public void save(User entity) {

    }

    @Override
    public void update(User entity) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public User findByUsername(String username) {
        return null;
    }
}

package edu.school21.sockets.repositories;

import edu.school21.sockets.models.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Component
@Qualifier("usersRepositoryImpl")
public class UsersRepositoryImpl implements UsersRepository {
    JdbcTemplate jdbcTemplate;
    private final String SQL_FIND_BY_ID = "SELECT * FROM users WHERE id = ?";
    private final String SQL_FIND_BY_USERNAME = "SELECT * FROM users WHERE username = ?";
    private final String SQL_FIND_ALL = "SELECT * FROM users";
    private final String SQL_SAVE = "INSERT INTO users (username, password) VALUES (?, ?)";
    private final String SQL_UPDATE = "UPDATE users SET username = ?, password = ? WHERE id = ?";
    private final String SQL_DELETE = "DELETE FROM users WHERE id = ?";

    public UsersRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<User> findById(Long id) {
        return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{id}, (resultSet, i) -> {
            User user = new User(resultSet.getLong("id"), resultSet.getString("username"), resultSet.getString("password"));
            return Optional.of(user);
        });
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, (resultSet, i) -> {
            return new User(resultSet.getLong("id"), resultSet.getString("username"), resultSet.getString("password"));
        });
    }

    @Override
    public void save(User entity) {
        jdbcTemplate.update(SQL_SAVE, entity.getId(), entity.getUsername(), entity.getPassword());
    }

    @Override
    public void update(User entity) {
        jdbcTemplate.update(SQL_UPDATE, entity.getUsername(), entity.getPassword(), entity.getId());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(SQL_DELETE, id);
    }

    @Override
    public User findByUsername(String username) {
        return jdbcTemplate.queryForObject(SQL_FIND_BY_USERNAME, new Object[]{username}, (resultSet, i) -> {
            User user = new User(resultSet.getLong("id"), resultSet.getString("username"), resultSet.getString("password"));
            return user;
        });
    }
}

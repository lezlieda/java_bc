package edu.school21.spring.service.repositories;

import edu.school21.spring.service.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Component
@Qualifier("usersRepositoryJdbcTemplateImpl")
public class UsersRepositoryJdbcTemplateImpl implements UsersRepository {
    JdbcTemplate jdbcTemplate;
    private final String SQL_FIND_BY_ID = "SELECT * FROM day08.users WHERE id = ?";
    private final String SQL_FIND_BY_EMAIL = "SELECT * FROM day08.users WHERE email = ?";
    private final String SQL_FIND_ALL = "SELECT * FROM day08.users";
    private final String SQL_SAVE = "INSERT INTO day08.users (id, email, password) VALUES (?, ?, ?)";
    private final String SQL_UPDATE = "UPDATE day08.users SET email = ?, password = ? WHERE id = ?";
    private final String SQL_DELETE = "DELETE FROM day08.users WHERE id = ?";

    @Autowired
    public UsersRepositoryJdbcTemplateImpl(@Qualifier("driverManagerDataSource")DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<User> findById(Long id) {
        return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{id}, (resultSet, i) -> {
            User user = new User(resultSet.getLong("id"), resultSet.getString("email"), resultSet.getString("password"));
            return Optional.of(user);
        });
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, (resultSet, i) -> new User(resultSet.getLong("id"), resultSet.getString("email"), resultSet.getString("password")));
    }

    @Override
    public void save(User entity) {
        jdbcTemplate.update(SQL_SAVE, entity.getId(), entity.getEmail(), entity.getPassword());
    }

    @Override
    public void update(User entity) {
        jdbcTemplate.update(SQL_UPDATE, entity.getEmail(), entity.getPassword(), entity.getId());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(SQL_DELETE, id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jdbcTemplate.queryForObject(SQL_FIND_BY_EMAIL, new Object[]{email}, (resultSet, i) -> {
            User user = new User(resultSet.getLong("id"), resultSet.getString("email"), resultSet.getString("password"));
            return Optional.of(user);
        });
    }

}

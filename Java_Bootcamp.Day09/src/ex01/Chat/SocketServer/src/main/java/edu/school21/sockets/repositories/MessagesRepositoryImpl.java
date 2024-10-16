package edu.school21.sockets.repositories;

import edu.school21.sockets.models.Message;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Optional;

@Component
@Qualifier("messagesRepositoryImpl")
public class MessagesRepositoryImpl implements MessagesRepository {

    JdbcTemplate jdbcTemplate;

    private final String SQL_FIND_BY_ID = "SELECT * FROM \"messages\" WHERE id = ?";
    private final String SQL_SAVE = "INSERT INTO \"messages\" (user_id, message) VALUES (?, ?)";
    private final String SQL_MAX_ID = "SELECT MAX(id) FROM \"messages\"";

    public MessagesRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<Message> findById(Long id) {
        try {
            return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{id}, (resultSet, i) -> {
                Message message = new Message(resultSet.getLong("id"), null, resultSet.getString("message"), resultSet.getTimestamp("created_at").toLocalDateTime());
                return Optional.of(message);
            });
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return Optional.empty();

        }
    }

    @Override
    public long save(Message message) {
        try {
            if (jdbcTemplate.update(SQL_SAVE, message.getAuthor().getId(), message.getText()) == 1)
                return jdbcTemplate.queryForObject(SQL_MAX_ID, Long.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return -1;
    }

}

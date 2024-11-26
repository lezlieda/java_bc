package edu.school21.sockets.repositories;

import edu.school21.sockets.models.Chatroom;
import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Component
@Qualifier("messagesRepositoryImpl")
public class MessagesRepositoryImpl implements MessagesRepository {
    JdbcTemplate jdbcTemplate;
    private final String SQL_FIND_BY_ID = "WITH t1 AS (SELECT m.id,\n" +
            "                   m.chatroom_id,\n" +
            "                   m.user_id  AS author_id,\n" +
            "                   u.username AS author_username,\n" +
            "                   u.password AS author_password,\n" +
            "                   m.message,\n" +
            "                   m.created_at\n" +
            "            FROM \"messages\" m\n" +
            "                     JOIN \"users\" u on u.id = m.user_id),\n" +
            "     t2 AS (SELECT c.id       AS chatroom_id,\n" +
            "                   c.name,\n" +
            "                   c.owner    AS owner_id,\n" +
            "                   u.username AS owner_name,\n" +
            "                   u.password AS owner_password\n" +
            "            FROM \"chatrooms\" c\n" +
            "                     JOIN \"users\" u ON u.id = c.owner)\n" +
            "SELECT *\n" +
            "FROM t1\n" +
            "         JOIN t2 ON t1.chatroom_id = t2.chatroom_id WHERE id = ?";
    private final String SQL_SAVE = "INSERT INTO \"messages\" (chatroom_id, user_id, message) VALUES (?, ?, ?)";
    private final String SQL_MAX_ID = "SELECT MAX(id) FROM \"messages\"";
    private final String SQL_FIND_ALL = "SELECT * FROM \"messages\"";
    private final String SQL_CREATED_AT = "SELECT created_at FROM \"messages\" WHERE id = ?";
    private final String SQL_UPDATE = "UPDATE \"messages\" SET user_id =?, message =? WHERE id =?";
    private final String SQL_DELETE = "DELETE FROM \"messages\" WHERE id =?";
    private final String SQL_LAST_30_MESSAGES = "SELECT m.id, m.message, m.user_id, u.username, m.created_at FROM \"messages\" m JOIN \"users\" u ON m.user_id = u.id WHERE chatroom_id = ? ORDER BY created_at LIMIT 30";

    public MessagesRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<Message> findById(Long id) {
        try {
            return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{id}, (resultSet, i) -> {
                User user = new User(resultSet.getLong("author_id"), resultSet.getString("author_username"), resultSet.getString("author_password"));
                Chatroom chatroom = new Chatroom(resultSet.getLong("chatroom_id"), resultSet.getString("name"), user, null);
                return Optional.of(new Message(resultSet.getLong("id"), user, chatroom, resultSet.getString("message"), resultSet.getTimestamp("created_at").toLocalDateTime()));
            });
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Long save(Message message) {
        try {
            if (jdbcTemplate.update(SQL_SAVE, message.getChatroom().getId(), message.getAuthor().getId(), message.getText()) == 1) {
                Long id = jdbcTemplate.queryForObject(SQL_MAX_ID, Long.class);
                Timestamp createdAt = jdbcTemplate.queryForObject(SQL_CREATED_AT, Timestamp.class, id);
                message.setDateTime(createdAt.toLocalDateTime());
                message.setId(id);
                return id;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void update(Message entity) {
        jdbcTemplate.update(SQL_UPDATE, entity.getAuthor().getId(), entity.getText(), entity.getId());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(SQL_DELETE, id);
    }

    @Override
    public List<Message> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, (resultSet, i) -> new Message(resultSet.getLong("id"), null, null, resultSet.getString("message"), resultSet.getTimestamp("created_at").toLocalDateTime()));
    }

    @Override
    public List<Message> getLast30Messages(Long chatroomId) {
        return jdbcTemplate.query(SQL_LAST_30_MESSAGES, new Object[]{chatroomId}, (resultSet, i) -> new Message(resultSet.getLong("id"), new User(resultSet.getLong("user_id"), resultSet.getString("username"), null), null, resultSet.getString("message"), resultSet.getTimestamp("created_at").toLocalDateTime()));
    }


}

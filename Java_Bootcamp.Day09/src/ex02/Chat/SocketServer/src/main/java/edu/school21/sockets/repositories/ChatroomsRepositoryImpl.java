package edu.school21.sockets.repositories;

import edu.school21.sockets.models.Chatroom;
import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Component
@Qualifier("chatroomsRepositoryImpl")
public class ChatroomsRepositoryImpl implements ChatroomsRepository {
    JdbcTemplate jdbcTemplate;
    private final String SQL_FIND_BY_ID = "SELECT c.id, c.name, u.id AS owner_id, u.username AS owner_username, u.password AS owner_password FROM \"chatrooms\" c JOIN \"users\" u ON c.owner = u.id WHERE c.id = ?";
    private final String SQL_FIND_ALL = "SELECT c.id, c.name, u.id AS owner_id, u.username AS owner_username, u.password AS owner_password FROM \"chatrooms\" c JOIN \"users\" u ON c.owner = u.id";
    private final String SQL_SAVE = "INSERT INTO \"chatrooms\" (name, owner) VALUES (?,?)";
    private final String SQL_MAX_ID = "SELECT MAX(id) FROM \"chatrooms\"";
    private final String SQL_UPDATE = "UPDATE \"chatrooms\" SET name =?, owner =? WHERE id =?";
    private final String SQL_DELETE = "DELETE FROM \"chatrooms\" WHERE id =?";

    public ChatroomsRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<Chatroom> findById(Long id) {
        try {
            return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{id}, (resultSet, i) -> {
                Long chatroomId = resultSet.getLong("id");
                String name = resultSet.getString("name");
                User owner = new User(resultSet.getLong("owner_id"), resultSet.getString("owner_username"), resultSet.getString("owner_password"));
                Chatroom res = new Chatroom(chatroomId, name, owner, null);
                return Optional.of(res);
            });
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Chatroom> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, (resultSet, i) -> {
            Long chatroomId = resultSet.getLong("id");
            String name = resultSet.getString("name");
            User owner = new User(resultSet.getLong("owner_id"), resultSet.getString("owner_username"), resultSet.getString("owner_password"));
            Chatroom res = new Chatroom(chatroomId, name, owner, null);
            return res;
        });
    }

    @Override
    public Long save(Chatroom entity) {
        if (jdbcTemplate.update(SQL_SAVE, entity.getName(), entity.getOwner().getId()) == 1) {
            return jdbcTemplate.queryForObject(SQL_MAX_ID, Long.class);
        }
        return null;
    }

    @Override
    public void update(Chatroom entity) {
        jdbcTemplate.update(SQL_UPDATE, entity.getName(), entity.getOwner().getId(), entity.getId());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(SQL_DELETE, id);
    }
}

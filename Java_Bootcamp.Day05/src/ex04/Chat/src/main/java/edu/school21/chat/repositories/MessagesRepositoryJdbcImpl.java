package edu.school21.chat.repositories;

import edu.school21.chat.exceptions.NotSavedSubEntityException;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {
    private final DataSource dataSource;
    private final String SQL_FIND_MESSAGE_BY_ID = "SELECT * FROM messages WHERE id = ?";
    private final String SQL_FIND_USER_BY_ID = "SELECT * FROM users WHERE id = ?";
    private final String SQL_FIND_CHATROOM_BY_ID = "SELECT * FROM chatrooms WHERE id = ?";
    private final String SQL_INSERT_INTO_MESSAGES = "INSERT INTO messages  (author, room, \"text\", date_time) VALUES (?, ?, ?, ?)";
    private final String SQL_GET_LAST_MESSAGE_ID = "SELECT MAX(id) FROM messages";

    public MessagesRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Message> findById(Long id) {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_MESSAGE_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                return Optional.of(new Message(id, findUserById(result.getLong(2)),
                        findChatroomById(result.getLong(3)),
                        result.getString(4),
                        result.getTimestamp(5).toLocalDateTime()));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }
    @Contract("_ -> new")
    private @NotNull User findUserById(Long id) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_USER_BY_ID);
        preparedStatement.setLong(1, id);
        ResultSet result = preparedStatement.executeQuery();
        if (result.next()) return new User(id, result.getString(2), result.getString(3), null, null);
        throw new SQLException("User not found!");
    }

    @Contract("_ -> new")
    private @NotNull Chatroom findChatroomById(Long id) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_CHATROOM_BY_ID);
        preparedStatement.setLong(1, id);
        ResultSet result = preparedStatement.executeQuery();
        if (result.next()) return new Chatroom(id, result.getString(2), findUserById(result.getLong(3)), null);
        throw new SQLException("Chatroom not found!");
    }

    @Override
    public void save(Message message) {
        checkMessageValidity(message);
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_INTO_MESSAGES);
            preparedStatement.setLong(1, message.getAuthor().getId());
            preparedStatement.setLong(2, message.getRoom().getId());
            preparedStatement.setString(3, message.getText());
            preparedStatement.setObject(4, message.getDateTime());
            if (preparedStatement.executeUpdate() == 0) {
                throw new NotSavedSubEntityException("Message not saved!");
            } else {
                preparedStatement = connection.prepareStatement(SQL_GET_LAST_MESSAGE_ID);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) message.setId(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            throw new NotSavedSubEntityException(e.getMessage());
        }
    }

    private void checkMessageValidity(@NotNull Message message) throws NotSavedSubEntityException {
        Long authorID = message.getAuthor().getId();
        Long chatroomID = message.getRoom().getId();
        if (authorID == null) throw new NotSavedSubEntityException("Author ID is NULL");
        if (chatroomID == null) throw new NotSavedSubEntityException("Chatroom ID is NULL");
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_USER_BY_ID);
            preparedStatement.setLong(1, authorID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) throw new NotSavedSubEntityException("No such Author");
            preparedStatement = connection.prepareStatement(SQL_FIND_CHATROOM_BY_ID);
            preparedStatement.setLong(1, chatroomID);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) throw new NotSavedSubEntityException("No such chatroom");
        } catch (SQLException e) {
        }
    }

    @Override
    public void update(Message message) {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(createStatement(message));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private @NotNull String createStatement(@NotNull Message message) {
        StringBuilder result = new StringBuilder("UPDATE messages SET author = ");
        if (message.getAuthor() != null) {
            result.append(message.getAuthor().getId().toString());
        } else {
            result.append("null");
        }
        result.append(", room = ");
        if (message.getRoom() != null) {
            result.append(message.getRoom().getId().toString());
        } else {
            result.append("null");
        }
        result.append(", text = ");
        if (message.getText() != null) {
            result.append("'" + message.getText() + "'");
        } else {
            result.append("null");
        }
        result.append(", date_time = ");
        if (message.getDateTime() != null) {
            result.append("'" + message.getDateTime() + "'");
        } else {
            result.append("null");
        }
        result.append(" WHERE id = " + message.getId());
        return result.toString();
    }
}

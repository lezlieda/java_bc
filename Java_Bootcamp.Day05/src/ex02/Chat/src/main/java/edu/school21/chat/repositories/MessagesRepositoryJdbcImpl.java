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

    public MessagesRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Message> findById(long id) {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM messages WHERE id = " + id);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                return Optional.of(new Message(id, findUserById(result.getLong(2)), findChatroomById(result.getLong(3)),
                        result.getString(4), result.getTimestamp(5).toLocalDateTime()));
            }
        } catch (SQLException e) {
        }
        return Optional.empty();
    }

    @Contract("_ -> new")
    private @NotNull User findUserById(long id) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE id = " + id);
        ResultSet result = preparedStatement.executeQuery();
        if (result.next())
            return new User(id, result.getString(2), result.getString(3), null, null);
        throw new SQLException("User not found!");
    }

    @Contract("_ -> new")
    private @NotNull Chatroom findChatroomById(long id) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM chatrooms WHERE id = " + id);
        ResultSet result = preparedStatement.executeQuery();
        if (result.next())
            return new Chatroom(id, result.getString(2), findUserById(result.getLong(3)), null);
        throw new SQLException("Chatroom not found!");
    }

    @Override
    public void save(Message message) {
        checkMessageValidity(message);
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO messages (author, room, \"text\", date_time) " +
                            "VALUES (" + message.getAuthor().getId() + ", " +
                            message.getRoom().getId() + ", '" +
                            message.getText() + "', '" + message.getDateTime().toString() + "')");
            if (preparedStatement.executeUpdate() == 0) {
                throw new NotSavedSubEntityException("Message not saved!");
            } else {
                preparedStatement = connection.prepareStatement("SELECT MAX(id) FROM messages");
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next())
                    message.setId(resultSet.getLong(1));
            }

        } catch (SQLException e) {
            throw new NotSavedSubEntityException(e.getMessage());
        }
    }

    private void checkMessageValidity(Message message) throws NotSavedSubEntityException {
        Long authorID = message.getAuthor().getId();
        Long chatroomID = message.getRoom().getId();
        if (authorID == null)
            throw new NotSavedSubEntityException("Author ID is NULL");
        if (chatroomID == null)
            throw new NotSavedSubEntityException("Chatroom ID is NULL");
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM users WHERE id = " + authorID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next())
                throw new NotSavedSubEntityException("No such Author");
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM chatrooms WHERE id = " + chatroomID);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next())
                throw new NotSavedSubEntityException("No such chatroom");

        } catch (SQLException e) {
        }

    }
}

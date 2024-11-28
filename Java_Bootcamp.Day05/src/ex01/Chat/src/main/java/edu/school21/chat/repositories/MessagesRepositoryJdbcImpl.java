package edu.school21.chat.repositories;

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
    private final String SQL_FIND_MESSAGE_BY_ID = "SELECT * FROM day05.messages WHERE id = ?";
    private final String SQL_FIND_USER_BY_ID = "SELECT * FROM day05.users WHERE id = ?";
    private final String SQL_FIND_CHATROOM_BY_ID = "SELECT * FROM day05.chatrooms WHERE id = ?";

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
                return Optional.of(new Message(id, findUserById(result.getLong(2)), findChatroomById(result.getLong(3)),
                        result.getString(4), result.getTimestamp(5).toLocalDateTime()));
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
        if (result.next())
            return new User(id, result.getString(2), result.getString(3), null, null);
        throw new SQLException("User not found!");
    }

    @Contract("_ -> new")
    private @NotNull Chatroom findChatroomById(Long id) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_CHATROOM_BY_ID);
        preparedStatement.setLong(1, id);
        ResultSet result = preparedStatement.executeQuery();
        if (result.next())
            return new Chatroom(id, result.getString(2), findUserById(result.getLong(3)), null);
        throw new SQLException("Chatroom not found!");
    }
}

package edu.school21.chat.repositories;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;

public class UsersRepositoryJdbcImpl implements UsersRepository {
    private DataSource dataSource;
    private final String SQL_FIND_ALL = "WITH t1 AS (SELECT u.id,\n" +
            "                   u.login,\n" +
            "                   u.password,\n" +
            "                   array_agg(DISTINCT c.id)   AS created_id,\n" +
            "                   array_agg(DISTINCT c.name) AS created_name,\n" +
            "                   array_agg(DISTINCT m.room) AS participant_id\n" +
            "            FROM users u\n" +
            "                     LEFT JOIN chatrooms c ON c.owner = u.id\n" +
            "                     LEFT JOIN messages m ON m.author = u.id\n" +
            "            GROUP BY 1)\n" +
            "SELECT t1.id,\n" +
            "       t1.login,\n" +
            "       t1.password,\n" +
            "       t1.created_id,\n" +
            "       t1.created_name,\n" +
            "       array_agg(DISTINCT c.id)   AS participant_id,\n" +
            "       array_agg(DISTINCT c.name) AS participant_name\n" +
            "FROM t1\n" +
            "         LEFT JOIN chatrooms c ON c.id = ANY (t1.participant_id)\n" +
            "GROUP BY 1, 2, 3, 4, 5;";

    public UsersRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<User> findAll(int page, int size) {
        if (size < 1 || page < 0 || page > size)
            throw new IllegalArgumentException("Invalid page or size");
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.wasNull()) {
                return null;
            } else {
                List<User> users = new ArrayList<>();
                while (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getLong("id"));
                    user.setLogin(resultSet.getString("login"));
                    user.setPassword(resultSet.getString("password"));
                    List<Chatroom> createdRooms = new ArrayList<>();
                    Object[] createdIds = (Object[]) resultSet.getArray("created_id").getArray();
                    Object[] createdNames = (Object[]) resultSet.getArray("created_name").getArray();
                    for (int i = 0; i < createdIds.length; i++) {
                        Chatroom chatroom = new Chatroom();
                        chatroom.setId((Long) createdIds[i]);
                        chatroom.setName((String) createdNames[i]);
                        createdRooms.add(chatroom);
                    }
                    user.setCreatedRooms(createdRooms);
                    List<Chatroom> participantRooms = new ArrayList<>();
                    Object[] participantIds = (Object[]) resultSet.getArray("participant_id").getArray();
                    Object[] participantNames = (Object[]) resultSet.getArray("participant_name").getArray();
                    for (int i = 0; i < participantIds.length; i++) {
                        Chatroom chatroom = new Chatroom();
                        chatroom.setId((Long) participantIds[i]);
                        chatroom.setName((String) participantNames[i]);
                        participantRooms.add(chatroom);
                    }
                    user.setRooms(participantRooms);
                    users.add(user);
                }
                List<User> result = new ArrayList<>();
                for (int i = page * size; i < users.size() && i < (page + 1) * size; i++) {
                    result.add(users.get(i));
                }
                return result;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


}

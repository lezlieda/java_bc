package edu.school21.sockets.server;

import edu.school21.sockets.models.User;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component("usersManager")
public class UsersManager {
    private static UsersManager instance = null;
    private final Map<Channel, User> loggedInUsers = new ConcurrentHashMap<>();
    private final Set<User> userList = new HashSet<>();

    public static UsersManager getInstance() {
        if(instance == null) {
            instance = new UsersManager();
        }
        return instance;
    }

    public void logIn(Channel channel, User user) {
        userList.add(user);
        loggedInUsers.put(channel, user);
    }

    public void logOut(Channel channel) {
        userList.remove(loggedInUsers.get(channel));
        loggedInUsers.remove(channel);
    }

    public Map<Channel, User> getLoggedInUsers() {
        return loggedInUsers;
    }

    public String getUsername(Channel channel) {
        return loggedInUsers.get(channel).getUsername();
    }

    public ChannelGroup getRecipients(Channel sender) {
        ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
        loggedInUsers.forEach((channel, user) -> {
            if (!sender.equals(channel))
                channels.add(channel);
        });
        return channels;
    }

    public boolean isUserLoggedIn(User user) {
        for (User u : userList) {
            if (u.equals(user))
                return true;
        }
        return false;
    }

}

package edu.school21.sockets.managers;

import edu.school21.sockets.models.Chatroom;
import edu.school21.sockets.models.User;
import edu.school21.sockets.services.ChatroomsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component("chatroomsManager" )
public class ChatroomsManager {
    @Autowired
    private ChatroomsService chatroomsService;
    private static ChatroomsManager instance = null;
    private final Map<Long, Long> chatroomUserRoomMapOnline = new ConcurrentHashMap<>();
    private final Map<Long, Long> chatroomUserRoomMapArchived = new ConcurrentHashMap<>();


    public static ChatroomsManager getInstance() {
        if (instance == null) {
            instance = new ChatroomsManager();
        }
        return instance;
    }

    public void enterChatroom(User user, Chatroom chatroom) {
        chatroomUserRoomMapOnline.put(user.getId(), chatroom.getId());
        chatroomUserRoomMapArchived.put(user.getId(), chatroom.getId());
    }

    public Long lastRoomVisited(User user) {
        return chatroomUserRoomMapArchived.get(user.getId());
    }

    public void leaveChatroom(User user) {
        chatroomUserRoomMapOnline.remove(user.getId());
    }

    public Long getChatroomIdByUserId(Long userId) {
        return chatroomUserRoomMapOnline.get(userId);
    }

    public boolean createChatroom(String name, User owner) {
        return chatroomsService.createChatroom(name, owner);
    }

    public List<Chatroom> getAllChatrooms() {
        return chatroomsService.getAllChatrooms();
    }

    public String getChatroomList() {
        return chatroomsService.getChatroomList();
    }

    public Chatroom getChatroomById(Long id) {
        return chatroomsService.getChatroomById(id);
    }

    public List<Long> getUsersInChatroom(Long chatroomId) {
        List<Long> usersInChatroom = new ArrayList<>();
        chatroomUserRoomMapOnline.forEach((userId, roomId) -> {
            if (roomId.equals(chatroomId)) {
                usersInChatroom.add(userId);
            }
        });
        return usersInChatroom;
    }
}

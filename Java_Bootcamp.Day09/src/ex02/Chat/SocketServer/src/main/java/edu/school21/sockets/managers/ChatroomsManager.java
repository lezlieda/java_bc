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
    private final Map<Long, Long> chatroomUserRoomMap = new ConcurrentHashMap<>();


    public static ChatroomsManager getInstance() {
        if (instance == null) {
            instance = new ChatroomsManager();
        }
        return instance;
    }

    public void enterChatroom(User user, Chatroom chatroom) {
        chatroomUserRoomMap.put(user.getId(), chatroom.getId());
    }

    public void leaveChatroom(User user) {
        chatroomUserRoomMap.remove(user.getId());
    }

    public Long getChatroomIdByUserId(Long userId) {
        return chatroomUserRoomMap.get(userId);
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
        chatroomUserRoomMap.forEach((userId, roomId) -> {
            if (roomId.equals(chatroomId)) {
                usersInChatroom.add(userId);
            }
        });
        return usersInChatroom;
    }
}

package edu.school21.sockets.services;

import edu.school21.sockets.models.Chatroom;
import edu.school21.sockets.models.User;

import java.util.List;

public interface ChatroomsService {
    boolean createChatroom(String name, User owner);
    Chatroom getChatroomById(Long id);
    List<Chatroom> getAllChatrooms();
    String getChatroomList();

}

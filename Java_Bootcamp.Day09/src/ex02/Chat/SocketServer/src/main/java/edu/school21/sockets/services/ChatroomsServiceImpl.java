package edu.school21.sockets.services;

import edu.school21.sockets.models.Chatroom;
import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.ChatroomsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("chatroomsService")
public class ChatroomsServiceImpl implements ChatroomsService {
    private final ChatroomsRepository chatroomsRepository;

    @Autowired
    public ChatroomsServiceImpl(@Qualifier("chatroomsRepository") ChatroomsRepository chatroomsRepository) {
        this.chatroomsRepository = chatroomsRepository;
    }

    @Override
    public boolean createChatroom(String name, User owner) {
        Chatroom chatroom = new Chatroom(null, name, owner, null);
        return chatroomsRepository.save(chatroom) != null;
    }

    @Override
    public Chatroom getChatroomById(Long id) {
        return chatroomsRepository.findById(id).orElse(null);
    }

    @Override
    public List<Chatroom> getAllChatrooms() {
        return chatroomsRepository.findAll();
    }

    @Override
    public String getChatroomList() {
        StringBuilder sb = new StringBuilder();
        for (Chatroom chatroom : chatroomsRepository.findAll()) {
            sb.append(chatroom.getId()).append(". ").append(chatroom.getName()).append("\n");
        }
        return sb.toString();
    }


}

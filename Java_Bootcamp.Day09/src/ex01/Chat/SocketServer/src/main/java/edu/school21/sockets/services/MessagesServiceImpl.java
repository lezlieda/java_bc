package edu.school21.sockets.services;

import edu.school21.sockets.models.Message;
import edu.school21.sockets.repositories.MessagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("messagesService")
public class MessagesServiceImpl implements MessagesService {
    private final MessagesRepository messagesRepository;

    @Autowired
    public MessagesServiceImpl(@Qualifier("messagesRepository") MessagesRepository messagesRepository) {
        this.messagesRepository = messagesRepository;
    }

    @Override
    public boolean saveMessage(Message message) {
        return messagesRepository.save(message) != null;
    }
}

package edu.school21.sockets.services;

import edu.school21.sockets.models.Message;
import edu.school21.sockets.repositories.MessagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("messagesService")
public class MessagesServiceImpl implements MessagesService {
    @Autowired
    @Qualifier("messagesRepositoryImpl")
    private final MessagesRepository messagesRepository;

    public MessagesServiceImpl(MessagesRepository messagesRepository) {
        this.messagesRepository = messagesRepository;
    }

    @Override
    public boolean saveMessage(Message message) {
        return messagesRepository.save(message) > 0;
    }
}

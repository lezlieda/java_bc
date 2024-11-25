package edu.school21.sockets.managers;

import edu.school21.sockets.models.Message;
import edu.school21.sockets.services.MessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("messagesManager")
public class MessagesManager {
    @Autowired
    private MessagesService messagesService;
    private static MessagesManager instance = null;

    public static MessagesManager getInstance() {
        if (instance == null) {
            instance = new MessagesManager();
        }
        return instance;
    }

    public void sendMessage(Message message) {
        messagesService.saveMessage(message);
    }

}

package edu.school21.sockets.models;

import java.time.LocalDateTime;
import java.util.Objects;

public class Message {
    private Long id;
    private User author;
    private Chatroom chatroom;
    private String text;
    private LocalDateTime dateTime;

    public Message() {
    }

    public Message(User author, String text) {
        this.author = author;
        this.text = text;
    }

    public Message(Long id, User author, Chatroom chatroom, String text, LocalDateTime dateTime) {
        this.id = id;
        this.author = author;
        this.chatroom = chatroom;
        this.text = text;
        this.dateTime = dateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Chatroom getChatroom() {
        return chatroom;
    }

    public void setChatroom(Chatroom chatroom) {
        this.chatroom = chatroom;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        if (!getId().equals(message.getId())) return false;
        if (getAuthor() != null ? !getAuthor().equals(message.getAuthor()) : message.getAuthor() != null) return false;
        if (getChatroom() != null ? !getChatroom().equals(message.getChatroom()) : message.getChatroom() != null)
            return false;
        if (getText() != null ? !getText().equals(message.getText()) : message.getText() != null) return false;
        return getDateTime().equals(message.getDateTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAuthor(), getChatroom(), getText(), getDateTime());
    }

    @Override
    public String toString() {
        return author.getUsername() + ": " + text;
    }

}

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
      return Objects.equals(getId(), message.getId()) && Objects.equals(getAuthor(), message.getAuthor()) && Objects.equals(getChatroom(), message.getChatroom()) && Objects.equals(getText(), message.getText()) && Objects.equals(getDateTime(), message.getDateTime());
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
package edu.school21.chat.exceptions;

public class NotSavedSubEntityException extends RuntimeException {
    public NotSavedSubEntityException(String e) {
        super(e);
    }
}

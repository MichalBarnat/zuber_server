package com.bbc.zuber.exceptions;

public class ConversationNotFoundException extends RuntimeException {

    public ConversationNotFoundException() {
    }

    public ConversationNotFoundException(String message) {
        super(message);
    }
}

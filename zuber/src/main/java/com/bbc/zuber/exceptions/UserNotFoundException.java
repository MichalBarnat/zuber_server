package com.bbc.zuber.exceptions;

public class UserNotFoundException extends RuntimeException{
    private static final String ERROR_MESSAGE = "User with id: %d not found!";

    public UserNotFoundException(long id) {
        super(String.format(ERROR_MESSAGE, id));
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}

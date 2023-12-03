package com.bbc.zuber.exceptions;

public class DriversNotAvailableException extends RuntimeException{
    public DriversNotAvailableException() {
    }

    public DriversNotAvailableException(String message) {
        super(message);
    }
}

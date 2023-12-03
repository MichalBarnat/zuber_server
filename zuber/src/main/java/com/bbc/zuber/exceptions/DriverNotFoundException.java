package com.bbc.zuber.exceptions;

public class DriverNotFoundException extends RuntimeException{
    public DriverNotFoundException() {
    }

    public DriverNotFoundException(String message) {
        super(message);
    }
}

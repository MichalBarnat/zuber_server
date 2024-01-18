package com.bbc.zuber.exceptions;

public class RideInfoNotFoundException extends RuntimeException {

    public RideInfoNotFoundException() {
    }

    public RideInfoNotFoundException(String message) {
        super(message);
    }
}

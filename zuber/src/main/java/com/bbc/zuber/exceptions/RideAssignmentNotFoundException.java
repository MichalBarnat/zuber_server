package com.bbc.zuber.exceptions;

public class RideAssignmentNotFoundException extends RuntimeException{
    public RideAssignmentNotFoundException() {
    }

    public RideAssignmentNotFoundException(String message) {
        super(message);
    }
}

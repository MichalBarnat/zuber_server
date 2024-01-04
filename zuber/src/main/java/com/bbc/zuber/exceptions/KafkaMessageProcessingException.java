package com.bbc.zuber.exceptions;

public class KafkaMessageProcessingException extends RuntimeException {
    public KafkaMessageProcessingException() {
    }

    public KafkaMessageProcessingException(String message) {
        super(message);
    }
}
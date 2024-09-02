package com.example.university.exception;

public class EntityAlreadyAddedException extends RuntimeException {

    public EntityAlreadyAddedException(String message) {
        super(message);
    }
}

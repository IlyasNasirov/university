package com.example.university.exception;

public class NoEntityFoundException extends RuntimeException{

    public NoEntityFoundException(String message) {
        super(message);
    }
}

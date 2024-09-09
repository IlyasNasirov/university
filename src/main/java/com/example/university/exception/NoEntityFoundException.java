package com.example.university.exception;

/**
 * Custom exception thrown when no entity is found in the system.
 *
 * <p>This exception is a subclass of {@link RuntimeException} and is used to indicate that
 * a requested entity, such as a student, teacher, or subject, could not be found.
 */

public class NoEntityFoundException extends RuntimeException {

    public NoEntityFoundException(String message) {
        super(message);
    }
}

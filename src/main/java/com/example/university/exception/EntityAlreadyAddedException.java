package com.example.university.exception;

/**
 * Custom exception thrown when attempting to add an entity that already exists.
 *
 * <p>This exception is a subclass of {@link RuntimeException} and is used to indicate that
 * an entity, such as a student, teacher, or subject, has already been added to the system.
 */
public class EntityAlreadyAddedException extends RuntimeException {

    public EntityAlreadyAddedException(String message) {
        super(message);
    }
}

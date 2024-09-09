package com.example.university.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for the application.
 *
 * <p>This class is annotated with {@code @RestControllerAdvice} to handle exceptions thrown
 * by controllers across the entire application. It centralizes the exception handling logic
 * and provides custom responses for different types of exceptions.
 */
@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(NoEntityFoundException.class)
    ResponseEntity<String> handleNoEntityFoundException(NoEntityFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityAlreadyAddedException.class)
    ResponseEntity<String> handleEntityAlreadyAddedException(EntityAlreadyAddedException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }
    /**
     * Handles {@link MethodArgumentNotValidException} exceptions.
     *
     * <p>This method is triggered when validation errors occur on method arguments in the controller.
     * It collects all field errors, maps them to a key-value structure where the key is the field name
     * and the value is the error message, and returns a response with a {@code BAD_REQUEST} status.
     *
     * @param ex the exception that contains details about the validation errors
     * @return a {@link ResponseEntity} containing a map of field errors and their messages,
     *         along with the {@code BAD_REQUEST} HTTP status
     */

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

}

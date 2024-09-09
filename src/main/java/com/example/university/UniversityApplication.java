package com.example.university;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The entry point for the University application.
 *
 * <p>This class is annotated with {@code @SpringBootApplication}, which enables auto-configuration,
 * component scanning, and configuration for the Spring Boot application. The {@code main} method
 * is the entry point of the application, and it launches the Spring Boot application by calling
 * {@link SpringApplication#run(Class, String...)}.
 */

@SpringBootApplication
public class UniversityApplication {

    public static void main(String[] args) {
        SpringApplication.run(UniversityApplication.class, args);
    }

}

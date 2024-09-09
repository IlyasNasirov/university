package com.example.university.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


/**
 * Data Transfer Object (DTO) for a teacher.
 *
 * <p>This class is used to transfer teacher data between different layers or components
 * of the application. It includes basic teacher information and validation constraints
 * for the fields.
 */

@Getter
@Setter
@EqualsAndHashCode
@Builder
public class TeacherDto {

    private int id;

    @NotNull(message = "First name cannot be null")
    private String firstName;

    @NotNull(message = "Last name cannot be null")
    private String lastName;

    @NotNull(message = "Middle name cannot be null")
    private String middleName;

    @Min(value = 1, message = "Age must be greater than 0")
    private int age;
}

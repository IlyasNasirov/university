package com.example.university.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class StudentDto {

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

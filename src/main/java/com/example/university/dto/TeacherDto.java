package com.example.university.dto;

import com.example.university.entity.Student;
import com.example.university.entity.Subject;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Getter
@Setter
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

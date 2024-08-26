package com.example.university.dto;

import com.sun.istack.NotNull;
import lombok.Data;

@Data
public class StudentDto {
    private int id;

    private String firstName;

    private String lastName;

    private String middleName;

    private int age;
}

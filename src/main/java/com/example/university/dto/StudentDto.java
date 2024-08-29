package com.example.university.dto;

import com.example.university.entity.Teacher;
import com.sun.istack.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class StudentDto {
    private int id;

    private String firstName;

    private String lastName;

    private String middleName;

    private int age;

}

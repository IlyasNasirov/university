package com.example.university.dto;

import com.example.university.entity.Subject;
import lombok.Data;

import java.util.Set;

@Data
public class TeacherDto {
    private int id;
    private String firstName;
    private String lastName;
    private String middleName;
    private int age;
    private Set<Subject> subjects;
}

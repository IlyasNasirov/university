package com.example.university.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name ="students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String lastName;
    private String middleName;
    private int age;

}

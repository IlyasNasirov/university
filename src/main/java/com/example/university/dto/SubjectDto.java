package com.example.university.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class SubjectDto {
    private int id;

    @NotNull(message = "Name cannot be null")
    private String name;

}

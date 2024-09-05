package com.example.university.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@EqualsAndHashCode
@Builder
public class SubjectDto {
    private int id;

    @NotNull(message = "Name cannot be null")
    private String name;

}

package com.example.university.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * Data Transfer Object (DTO) for a subject.
 *
 * <p>This class is used to transfer subject data between different layers or components
 * of the application. It includes basic subject information and validation constraints
 * for the fields.
 */

@Getter
@Setter
@EqualsAndHashCode
@Builder
public class SubjectDto {
    private int id;

    @NotNull(message = "Name cannot be null")
    private String name;

}

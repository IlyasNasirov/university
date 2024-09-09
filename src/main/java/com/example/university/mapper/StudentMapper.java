package com.example.university.mapper;

import com.example.university.dto.StudentDto;
import com.example.university.entity.Student;
import org.mapstruct.Mapper;

/**
 * Mapper interface for converting between {@link Student} entities and {@link StudentDto} DTOs.
 *
 * <p>This interface uses MapStruct to map properties between {@link Student} and {@link StudentDto}.
 * It is configured to be used as a Spring component through the {@code componentModel = "spring"} setting.
 *
 * <p>The {@link #dtoToEntity(StudentDto)} method converts a {@link StudentDto} to a {@link Student} entity.
 * The {@link #entityToDto(Student)} method converts a {@link Student} entity to a {@link StudentDto}.
 */
@Mapper(componentModel = "spring")
public interface StudentMapper {

    /**
     * Converts a {@link StudentDto} to a {@link Student} entity.
     *
     * @param studentDto the DTO to be converted
     * @return the corresponding {@link Student} entity
     */
    Student dtoToEntity(StudentDto studentDto);

    /**
     * Converts a {@link Student} entity to a {@link StudentDto}.
     *
     * @param student the entity to be converted
     * @return the corresponding {@link StudentDto}
     */
    StudentDto entityToDto(Student student);

}

package com.example.university.mapper;

import com.example.university.dto.TeacherDto;
import com.example.university.entity.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * Mapper interface for converting between {@link Teacher} entities and {@link TeacherDto} DTOs.
 *
 * <p>This interface uses MapStruct to map properties between {@link Teacher} and {@link TeacherDto}.
 * It is configured to be used as a Spring component through the {@code componentModel = "spring"} setting.
 *
 * <p>The {@link #dtoToEntity(TeacherDto)} method converts a {@link TeacherDto} to a {@link Teacher} entity.
 * The {@link #entityToDto(Teacher)} method converts a {@link Teacher} entity to a {@link TeacherDto}.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TeacherMapper {

    /**
     * Converts a {@link TeacherDto} to a {@link Teacher} entity.
     *
     * @param dto the DTO to be converted
     * @return the corresponding {@link Teacher} entity
     */
    Teacher dtoToEntity(TeacherDto dto);

    /**
     * Converts a {@link Teacher} entity to a {@link TeacherDto}.
     *
     * @param teacher the entity to be converted
     * @return the corresponding {@link TeacherDto}
     */
    TeacherDto entityToDto(Teacher teacher);

}


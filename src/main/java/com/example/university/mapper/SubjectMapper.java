package com.example.university.mapper;

import com.example.university.dto.SubjectDto;
import com.example.university.entity.Subject;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * Mapper interface for converting between {@link Subject} entities and {@link SubjectDto} DTOs.
 *
 * <p>This interface uses MapStruct to map properties between {@link Subject} and {@link SubjectDto}.
 * It is configured to be used as a Spring component through the {@code componentModel = "spring"} setting.
 *
 * <p>The {@link #dtoToEntity(SubjectDto)} method converts a {@link SubjectDto} to a {@link Subject} entity.
 * The {@link #entityToDto(Subject)} method converts a {@link Subject} entity to a {@link SubjectDto}.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SubjectMapper {

    /**
     * Converts a {@link SubjectDto} to a {@link Subject} entity.
     *
     * @param dto the DTO to be converted
     * @return the corresponding {@link Subject} entity
     */
    Subject dtoToEntity(SubjectDto dto);

    /**
     * Converts a {@link Subject} entity to a {@link SubjectDto}.
     *
     * @param subject the entity to be converted
     * @return the corresponding {@link SubjectDto}
     */
    SubjectDto entityToDto(Subject subject);

}


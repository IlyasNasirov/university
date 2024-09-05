package com.example.university.mapper;

import com.example.university.dto.SubjectDto;
import com.example.university.entity.Subject;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SubjectMapper {

    Subject dtoToEntity(SubjectDto dto);
    SubjectDto entityToDto(Subject subject);

}

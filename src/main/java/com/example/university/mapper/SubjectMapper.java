package com.example.university.mapper;

import com.example.university.dto.SubjectDto;
import com.example.university.entity.Subject;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SubjectMapper {

    SubjectMapper INSTANCE= Mappers.getMapper(SubjectMapper.class);

    Subject dtoToEntity(SubjectDto dto);
    SubjectDto entityToDto(Subject subject);

}

package com.example.university.mapper;

import com.example.university.dto.TeacherDto;
import com.example.university.entity.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TeacherMapper {

    TeacherMapper INSTANCE= Mappers.getMapper(TeacherMapper.class);

    Teacher dtoToEntity(TeacherDto dto);
    TeacherDto entityToDto(Teacher teacher);

}

package com.example.university.mapper;

import com.example.university.entity.Student;
import com.example.university.dto.StudentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface StudentMapper {

    StudentMapper INSTANCE= Mappers.getMapper(StudentMapper.class);

    Student dtoToEntity(StudentDto studentDto);
    StudentDto entityToDto(Student student);

}

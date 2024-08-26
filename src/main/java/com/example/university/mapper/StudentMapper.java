package com.example.university.mapper;

import com.example.university.entity.Student;
import com.example.university.dto.StudentDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StudentMapper {

    StudentMapper INSTANCE= Mappers.getMapper(StudentMapper.class);

    Student dtoToEntity(StudentDto studentDto);
    StudentDto entityToDto(Student student);

}

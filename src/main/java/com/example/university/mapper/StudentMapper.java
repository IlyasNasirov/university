package com.example.university.mapper;

import com.example.university.entity.Student;
import com.example.university.dto.StudentDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface StudentMapper {

    Student dtoToEntity(StudentDto studentDto);
    StudentDto entityToDto(Student student);

}

package com.example.university.service;

import com.example.university.dto.TeacherDto;
import com.example.university.entity.Teacher;
import com.example.university.mapper.TeacherMapper;
import com.example.university.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeacherService {

    @Autowired
    TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper=TeacherMapper.INSTANCE;

    public TeacherDto getTeacherById(int id){
        Optional<Teacher> optional=teacherRepository.findById(id);
        return optional.map(teacherMapper::entityToDto).orElse(null);
    }
    public List<TeacherDto> getAllTeachers(){
        List<Teacher> list=teacherRepository.findAll();
        return list.stream()
                .map(teacherMapper::entityToDto)
                .collect(Collectors.toList());
    }
    public TeacherDto saveTeacher(TeacherDto teacherDto){
        Teacher teacher=teacherMapper.dtoToEntity(teacherDto);
        teacherRepository.save(teacher);
        return teacherMapper.entityToDto(teacher);
    }
    public void deleteTeacher(int id){
        teacherRepository.deleteById(id);
    }


    public TeacherDto getTeacherByFirstName(String firstName) {
        Optional<Teacher> optional=teacherRepository.findByFirstName(firstName);
        return optional.map(teacherMapper::entityToDto).orElse(null);
    }
}

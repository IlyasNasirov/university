package com.example.university.service;

import com.example.university.dto.SubjectDto;
import com.example.university.dto.TeacherDto;
import com.example.university.entity.Subject;
import com.example.university.entity.Teacher;
import com.example.university.mapper.SubjectMapper;
import com.example.university.mapper.TeacherMapper;
import com.example.university.repository.SubjectRepository;
import com.example.university.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TeacherService {

    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    SubjectRepository subjectRepository;
    private final TeacherMapper teacherMapper = TeacherMapper.INSTANCE;

    public TeacherDto getTeacherById(int id) {
        Optional<Teacher> optional = teacherRepository.findById(id);
        return optional.map(teacherMapper::entityToDto).orElse(null);
    }

    public List<TeacherDto> getAllTeachers() {
        List<Teacher> list = teacherRepository.findAll();
        return list.stream()
                .map(teacherMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public TeacherDto saveTeacher(TeacherDto teacherDto) {
        Teacher teacher = teacherMapper.dtoToEntity(teacherDto);
        teacherRepository.save(teacher);
        return teacherMapper.entityToDto(teacher);
    }
    public void deleteTeacher(int id){
        teacherRepository.deleteById(id);
    }

    public TeacherDto findByIdOrName(String input) {
        try {
            int id = Integer.parseInt(input);
            Teacher teacher = teacherRepository.findById(id).orElse(null);
            return teacherMapper.entityToDto(teacher);
        } catch (NumberFormatException e) {
            Teacher teacher = teacherRepository.findByFirstNameOrLastNameIgnoreCase(input).orElse(null);
            return teacherMapper.entityToDto(teacher);
        }
    }

    public void addSubject(int id, Subject subject) {
        Teacher teacher = teacherRepository.findById(id).orElse(null);
        Optional<Subject> optional = subjectRepository.findByNameIgnoreCase(subject.getName());
        if(optional.isPresent()) {
            teacher.setSubjects(Set.of(optional.get()));
            subject.setTeacher(teacher);
            teacherRepository.save(teacher);
        }else {
            teacher.addSubject(subject);
            teacherRepository.save(teacher);
        }
    }

    public Set<SubjectDto> getAllSubjectsForTeacher(int id) {
        Teacher teacher = teacherRepository.findById(id).orElse(null);
        assert teacher != null;
        Set<Subject> subjects = teacher.getSubjects();
        return subjects.stream().map(SubjectMapper.INSTANCE::entityToDto).collect(Collectors.toSet());
    }

}

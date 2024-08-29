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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    SubjectRepository subjectRepository;

    TeacherMapper teacherMapper = TeacherMapper.INSTANCE;

    @Override
    public TeacherDto getTeacherById(int id) {
        Optional<Teacher> optional = teacherRepository.findById(id);
        return optional.map(teacherMapper::entityToDto).orElse(null);
    }

    @Override
    public List<TeacherDto> getAllTeachers() {
        List<Teacher> list = teacherRepository.findAll();
        return list.stream()
                .map(teacherMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TeacherDto saveTeacher(TeacherDto teacherDto) {
        Teacher teacher = teacherMapper.dtoToEntity(teacherDto);
        teacherRepository.save(teacher);
        return teacherMapper.entityToDto(teacher);
    }

    @Override
    public void deleteTeacher(int id) {
        teacherRepository.deleteById(id);
    }

    @Override
    public TeacherDto getTeacherByIdOrName(String input) {
        try {
            int id = Integer.parseInt(input);
            Teacher teacher = teacherRepository.findById(id).orElse(null);
            return teacherMapper.entityToDto(teacher);
        } catch (NumberFormatException e) {
            Teacher teacher = teacherRepository.findByFirstNameOrLastNameIgnoreCase(input).orElse(null);
            return teacherMapper.entityToDto(teacher);
        }
    }

    @Override
    public void addSubject(int id, SubjectDto subjectDto) {
        Subject subject = SubjectMapper.INSTANCE.dtoToEntity(subjectDto);
        Teacher teacher = teacherRepository.findById(id).orElse(null);
        teacher.getSubjects().add(subject);
        subject.setTeacher(teacher);
        teacherRepository.save(teacher);
    }

    @Override
    public void deleteSubject(int teacherId, int subjectId) {
        Subject subject=subjectRepository.findById(subjectId).orElse(null);
        Teacher teacher=teacherRepository.findById(teacherId).orElse(null);
        teacher.getSubjects().remove(subject);
        //check this
        teacherRepository.save(teacher);
    }

    @Override
    public List<SubjectDto> getAllSubjectsForTeacher(int id) {
        Teacher teacher = teacherRepository.findById(id).orElseThrow(() -> new RuntimeException("Teacher with id " + id + " not found"));
        assert teacher != null;
        List<Subject> subjects = teacher.getSubjects();
        return subjects.stream().map(SubjectMapper.INSTANCE::entityToDto).collect(Collectors.toList());
    }

    @Override
    public TeacherDto updateTeacher(int teacherId, TeacherDto dto) {
        Teacher teacher=teacherRepository.findById(teacherId).orElseThrow(()->new RuntimeException("Teacher is not exist"));
        teacher.setFirstName(dto.getFirstName());
        teacher.setLastName(dto.getLastName());
        teacher.setMiddleName(dto.getMiddleName());
        teacher.setAge(dto.getAge());
        teacherRepository.save(teacher);
        return teacherMapper.entityToDto(teacher);
    }

}

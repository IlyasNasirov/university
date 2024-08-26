package com.example.university.service;

import com.example.university.dto.StudentDto;
import com.example.university.entity.Student;
import com.example.university.mapper.StudentMapper;
import com.example.university.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepo;
    private final StudentMapper studentMapper = StudentMapper.INSTANCE;

    public StudentDto getStudentById(int id) {
        Optional<Student> optional = studentRepo.findById(id);
        return optional.map(studentMapper::EntityToDto).orElse(null);
    }

    public List<StudentDto> getAllStudents() {
        List<Student> list = studentRepo.findAll();
        return list.stream()
                .map(studentMapper::EntityToDto)
                .collect(Collectors.toList());
    }

    public StudentDto saveStudent(StudentDto stDto) {
        Student student = studentMapper.DtoToEntity(stDto);
        studentRepo.save(student);
        return studentMapper.EntityToDto(student);
    }

    public void deleteStudent(int id) {
        studentRepo.deleteById(id);
    }


}

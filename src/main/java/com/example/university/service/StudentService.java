package com.example.university.service;

import com.example.university.dto.StudentDto;
import com.example.university.dto.TeacherDto;

import java.util.List;

public interface StudentService {
    List<StudentDto> getAllStudents();

    StudentDto getStudentById(int id);

    StudentDto saveStudent(StudentDto stDto);

    void deleteStudent(int id);

    StudentDto updateStudent(int id, StudentDto studentDto);

    List<TeacherDto> getAllTeachersOfStudent(int studentId);

    void setTeacherForStudent(int studentId, int TeacherId);
}

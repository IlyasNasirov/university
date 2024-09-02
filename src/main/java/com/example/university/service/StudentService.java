package com.example.university.service;

import com.example.university.dto.StudentDto;
import com.example.university.dto.SubjectDto;
import com.example.university.dto.TeacherDto;

import java.util.List;

public interface StudentService {

    List<StudentDto> getAllStudents();

    StudentDto getStudentById(int id);

    StudentDto saveStudent(StudentDto stDto);

    void deleteStudent(int id);

    StudentDto updateStudent(int id, StudentDto studentDto);

    List<TeacherDto> getAllTeachersOfStudent(int studentId);

    void addTeacherToStudent(int studentId, int TeacherId);

    List<SubjectDto> getAllSubjectsOfStudent(int studentId);

    void addSubjectToStudent(int studentId, int subjectId);

    void deleteSubjectFromStudent(int studentId, int subjectId);

    void deleteTeacherFromStudent(int studentId, int teacherId);
}

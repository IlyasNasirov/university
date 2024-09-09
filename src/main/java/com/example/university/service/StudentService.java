package com.example.university.service;

import com.example.university.dto.StudentDto;
import com.example.university.dto.SubjectDto;
import com.example.university.dto.TeacherDto;
import java.util.List;

/**
 * Service interface for managing {@link com.example.university.entity.Student} entities.
 *
 * <p>This interface defines the contract for business logic related to students, such as
 * operations for creating, reading, updating, and deleting student data. Implementations
 * of this interface provide the actual business logic.
 */
public interface StudentService {

    List<StudentDto> getAllStudents();

    StudentDto getStudentById(int id);

    StudentDto saveStudent(StudentDto stDto);

    void deleteStudent(int id);

    StudentDto updateStudent(int id, StudentDto studentDto);

    List<TeacherDto> getAllTeachersOfStudent(int studentId);

    void addTeacherToStudent(int studentId, int teacherId);

    List<SubjectDto> getAllSubjectsOfStudent(int studentId);

    void addSubjectToStudent(int studentId, int subjectId);

    void deleteSubjectFromStudent(int studentId, int subjectId);

    void deleteTeacherFromStudent(int studentId, int teacherId);
}

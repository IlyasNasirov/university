package com.example.university.service;

import com.example.university.dto.StudentDto;
import com.example.university.dto.SubjectDto;
import com.example.university.dto.TeacherDto;

import java.util.List;

public interface TeacherService {

    List<TeacherDto> getAllTeachers();

    TeacherDto getTeacherById(int id);

    TeacherDto saveTeacher(TeacherDto teacherDto);

    TeacherDto updateTeacher(int id, TeacherDto teacherDto);

    void deleteTeacher(int id);

    void addSubjectToTeacher(int teacherId, SubjectDto subjectDto);

    List<SubjectDto> getAllSubjectsOfTeacher(int id);

    void deleteSubjectOfTeacher(int teacherId, int subjectId);

    List<StudentDto> getAllStudentsOfTeacher(int teacherId);

    void deleteStudentOfTeacher(int teacherId, int studentId);

    void addStudentToTeacher(int teacherId, int studentId);
}

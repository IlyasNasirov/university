package com.example.university.service;

import com.example.university.dto.SubjectDto;
import com.example.university.dto.TeacherDto;

import java.util.List;

public interface TeacherService {

    List<TeacherDto> getAllTeachers();

    TeacherDto getTeacherById(int id);

    TeacherDto saveTeacher(TeacherDto teacherDto);

    void deleteTeacher(int id);

    TeacherDto getTeacherByIdOrName(String input);

    void addSubject(int id, SubjectDto subjectDto);

    List<SubjectDto> getAllSubjectsForTeacher(int id);

    TeacherDto updateTeacher(int id, TeacherDto teacherDto);

    void deleteSubject(int teacherId, int subjectId);
}

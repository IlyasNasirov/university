package com.example.university.service;

import com.example.university.dto.SubjectDto;

import java.util.List;

public interface SubjectService {
    SubjectDto getSubjectById(int id);

    List<SubjectDto> getAllSubjects();

    SubjectDto saveSubject(SubjectDto Dto);

    void deleteSubject(int id);
    SubjectDto updateSubject(int id);
}

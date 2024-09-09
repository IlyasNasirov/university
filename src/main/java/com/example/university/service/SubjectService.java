package com.example.university.service;

import com.example.university.dto.SubjectDto;
import java.util.List;
/**
 * Service interface for managing {@link com.example.university.entity.Subject} entities.
 *
 * <p>This interface defines the contract for business logic related to subjects, including
 * operations for creating, reading, updating, and deleting subject data. Implementations
 * of this interface provide the actual business logic.
 */

public interface SubjectService {

    SubjectDto getSubjectById(int id);

    List<SubjectDto> getAllSubjects();

    SubjectDto saveSubject(SubjectDto dto);

    void deleteSubject(int id);

    SubjectDto updateSubject(int id, SubjectDto subjectDto);
}

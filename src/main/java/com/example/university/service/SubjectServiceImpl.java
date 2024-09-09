package com.example.university.service;

import com.example.university.dto.SubjectDto;
import com.example.university.entity.Subject;
import com.example.university.exception.NoEntityFoundException;
import com.example.university.mapper.SubjectMapper;
import com.example.university.repository.SubjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the {@link SubjectService} interface.
 *
 * <p>This class provides the business logic for managing subjects in the application.
 * It is annotated with {@code @Service} to indicate that it's a service component in the Spring context.
 * The {@code @AllArgsConstructor} annotation from Lombok generates a constructor with all required fields.
 */
@Service
@AllArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private SubjectRepository subjectRepository;

    private SubjectMapper subjectMapper;

    /**
     * Gets a subject by id
     *
     * @param id the id of the subject
     * @return the subject with the given id
     * @throws NoEntityFoundException if there is no subject with such id
     */
    public SubjectDto getSubjectById(int id) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new NoEntityFoundException("No such subject found with id: " + id));
        return subjectMapper.entityToDto(subject);
    }

    /**
     * Gets all subjects
     *
     * @return a list of all subjects
     */
    public List<SubjectDto> getAllSubjects() {
        List<Subject> list = subjectRepository.findAll();
        return list.stream()
                .map(subjectMapper::entityToDto)
                .collect(Collectors.toList());
    }

    /**
     * Creates a new subject
     *
     * @param dto the subject data transfer object
     * @return the created subject
     * @throws MethodArgumentNotValidException if the DTO is invalid
     */
    public SubjectDto saveSubject(SubjectDto dto) {
        Subject subject = subjectMapper.dtoToEntity(dto);
        subjectRepository.save(subject);
        return subjectMapper.entityToDto(subject);
    }

    /**
     * Deletes a subject by id
     *
     * @param id the id of the subject to be deleted
     * @throws NoEntityFoundException if there is no subject with such id
     */
    public void deleteSubject(int id) {
        subjectRepository.findById(id)
                .orElseThrow(() -> new NoEntityFoundException("No such subject found with id: " + id));
        subjectRepository.deleteById(id);
    }

    /**
     * Updates a subject by id
     *
     * @param id the id of the subject to be updated
     * @param subjectDto the updated subject data transfer object
     * @return the updated subjectDto
     * @throws NoEntityFoundException if there is no subject with such id
     */
    @Override
    public SubjectDto updateSubject(int id, SubjectDto subjectDto) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new NoEntityFoundException("No such subject found with id: " + id));
        subject.setName(subjectDto.getName());
        subjectRepository.save(subject);
        return subjectMapper.entityToDto(subject);
    }
}

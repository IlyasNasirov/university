package com.example.university.service;

import com.example.university.dto.SubjectDto;
import com.example.university.entity.Subject;
import com.example.university.exception.NoEntityFoundException;
import com.example.university.mapper.SubjectMapper;
import com.example.university.repository.SubjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private SubjectRepository subjectRepository;
    private SubjectMapper subjectMapper;

    public SubjectDto getSubjectById(int id) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new NoEntityFoundException("No such subject found with id: " + id));
        return subjectMapper.entityToDto(subject);
    }

    public List<SubjectDto> getAllSubjects() {
        List<Subject> list = subjectRepository.findAll();
        return list.stream()
                .map(subjectMapper::entityToDto)
                .collect(Collectors.toList());
    }

    public SubjectDto saveSubject(SubjectDto Dto) { //здесь обрабатывается тип исключение handleValidationExceptions
        Subject subject = subjectMapper.dtoToEntity(Dto);
        subjectRepository.save(subject);
        return subjectMapper.entityToDto(subject);
    }

    public void deleteSubject(int id) {
        subjectRepository.findById(id)
                .orElseThrow(() -> new NoEntityFoundException("No such subject found with id: " + id));
        subjectRepository.deleteById(id);
    }

    @Override
    public SubjectDto updateSubject(int id, SubjectDto subjectDto) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new NoEntityFoundException("No such subject found with id: " + id));
        subject.setName(subjectDto.getName());
        subjectRepository.save(subject);
        return subjectMapper.entityToDto(subject);
    }
}

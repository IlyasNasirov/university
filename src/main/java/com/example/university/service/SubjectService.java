package com.example.university.service;

import com.example.university.dto.StudentDto;
import com.example.university.dto.SubjectDto;
import com.example.university.entity.Student;
import com.example.university.entity.Subject;
import com.example.university.mapper.SubjectMapper;
import com.example.university.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubjectService {

    @Autowired
    SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper=SubjectMapper.INSTANCE;

    public SubjectDto getSubjectById(int id) {
        Optional<Subject> optional = subjectRepository.findById(id);
        return optional.map(subjectMapper::entityToDto).orElse(null);
    }

    public List<SubjectDto> getAllSubjects() {
        List<Subject> list = subjectRepository.findAll();
        return list.stream()
                .map(subjectMapper::entityToDto)
                .collect(Collectors.toList());
    }

    public SubjectDto saveSubject(SubjectDto Dto) {
        Subject subject = subjectMapper.dtoToEntity(Dto);
        subjectRepository.save(subject);
        return subjectMapper.entityToDto(subject);
    }

    public void deleteSubject(int id) {
        subjectRepository.deleteById(id);
    }

}

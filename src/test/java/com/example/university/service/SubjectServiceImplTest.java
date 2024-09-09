package com.example.university.service;

import com.example.university.dto.SubjectDto;
import com.example.university.entity.Subject;
import com.example.university.exception.NoEntityFoundException;
import com.example.university.mapper.SubjectMapper;
import com.example.university.repository.SubjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the {@link SubjectServiceImpl} class.
 *
 * <p>This class uses the {@code @ExtendWith(MockitoExtension.class)} annotation to enable Mockito’s
 * support for JUnit 5. It allows for the creation and management of mock objects, and facilitates
 * the injection of these mocks into the {@code SubjectServiceImpl} class being tested. The class
 * is designed to verify the functionality and behavior of the methods in {@code SubjectServiceImpl}.
 */

@ExtendWith(MockitoExtension.class)
class SubjectServiceImplTest {

    @InjectMocks
    private SubjectServiceImpl service;

    @Mock
    private SubjectRepository subjectRepository;

    @Mock
    private SubjectMapper subjectMapper;

    @Test
    void getSubjectById_WhenSubjectDoesNotExist_ShouldThrowNoEntityFoundException() {
        when(subjectRepository
                .findById(1))
                .thenReturn(Optional.empty());

        assertThrows(NoEntityFoundException.class, () -> service.getSubjectById(1));
        verify(subjectRepository, times(1)).findById(1);
    }

    @Test
    void getSubjectById_WhenSubjectExists_ReturnsSubject() {
        Subject subject = Subject
                .builder()
                .id(1)
                .name("Math")
                .build();

        when(subjectRepository
                .findById(1))
                .thenReturn(Optional.of(subject));

        assertEquals(subjectMapper.entityToDto(subject), service.getSubjectById(1));
        verify(subjectRepository, times(1)).findById(1);
    }

    @Test
    void getAllSubjects_WhenCalled_ReturnsListSubjectDtos() {
        List<Subject> subjects = List.of(
                Subject.builder().id(1).name("Math").build(),
                Subject.builder().id(2).name("Russian").build(),
                Subject.builder().id(3).name("English").build()
        );
        when(subjectRepository
                .findAll())
                .thenReturn(subjects);

        List<SubjectDto> expectedDtos = subjects
                .stream()
                .map(subjectMapper::entityToDto)
                .collect(Collectors.toList());

        List<SubjectDto> actualDtos = service.getAllSubjects();
        assertEquals(expectedDtos, actualDtos);
        verify(subjectRepository, times(1)).findAll();
    }

    @Test
    void saveSubject_WhenSubjectIsSaved_ReturnsSubjectDto() {
        Subject subject = Subject
                .builder()
                .id(1)
                .name("Math")
                .build();

        SubjectDto subjectDto = subjectMapper.entityToDto(subject);

        when(subjectMapper
                .dtoToEntity(subjectDto))
                .thenReturn(subject);
        when(subjectRepository
                .save(any(Subject.class)))
                .thenReturn(subject);
        when(subjectMapper
                .entityToDto(subject))
                .thenReturn(subjectDto);

        SubjectDto actualDto = service.saveSubject(subjectDto);
        assertEquals(subjectDto, actualDto);
        verify(subjectRepository, times(1)).save(any(Subject.class));
    }

    @Test
    void deleteSubject_WhenSubjectExists_ShouldDeleteSubject() {
        Subject subject = Subject
                .builder()
                .id(1)
                .name("Math")
                .build();

        when(subjectRepository
                .findById(subject.getId()))
                .thenReturn(Optional.of(subject));
        service.deleteSubject(subject.getId());
        verify(subjectRepository, times(1)).deleteById(subject.getId());
    }

    @Test
    void updateSubject_WhenSubjectExists_ShouldUpdateSubject() {
        Subject existingSubject = Subject
                .builder()
                .id(1)
                .name("Math")
                .build();
        SubjectDto subjectDto = SubjectDto
                .builder()
                .id(1)
                .name("Physics")
                .build();

        when(subjectRepository
                .findById(existingSubject.getId()))
                .thenReturn(Optional.of(existingSubject));
        when(subjectRepository
                .save(existingSubject))
                .thenReturn(existingSubject);
        when(subjectMapper
                .entityToDto(existingSubject))
                .thenReturn(subjectDto);

        SubjectDto updatedSubjectDto = service.updateSubject(existingSubject.getId(), subjectDto);
        assertEquals(subjectDto, updatedSubjectDto);

        verify(subjectRepository, times(1)).save(existingSubject);
        verify(subjectMapper, times(1)).entityToDto(existingSubject);
    }
}

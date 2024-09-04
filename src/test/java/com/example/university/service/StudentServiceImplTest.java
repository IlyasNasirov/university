package com.example.university.service;

import com.example.university.dto.StudentDto;
import com.example.university.entity.Student;
import com.example.university.exception.NoEntityFoundException;
import com.example.university.mapper.StudentMapper;
import com.example.university.repository.StudentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
public class StudentServiceImplTest {

    @InjectMocks
    private StudentServiceImpl service;
    @Mock
    private StudentRepository studentRepository;

    private Student st1;
    private Student st2;
    private Student st3;
    private final StudentMapper studentMapper = StudentMapper.INSTANCE;

    @BeforeEach
    void init() {
        st1 = Student.builder().id(1).firstName("ilyas").lastName("nasirov").middleName("urakbayevich").age(25).build();
        st2 = Student.builder().id(2).firstName("ivan").lastName("ivanov").middleName("ivanovich").age(31).build();
        st3 = Student.builder().id(3).firstName("mixail").lastName("jekov").middleName("alekseivich").age(37).build();
    }

    @Test
    void getAllStudents_WhenCalled_ReturnsListOfStudentDtos() {
        List<Student> students = List.of(st1, st2, st3);
        Mockito.when(studentRepository.findAll()).thenReturn(students);
        List<StudentDto> expectedDtos = students.stream()
                .map(studentMapper::entityToDto).collect(Collectors.toList());
        List<StudentDto> actualDtos = service.getAllStudents();
        Assertions.assertEquals(expectedDtos, actualDtos);
        Mockito.verify(studentRepository, Mockito.times(1)).findAll();
    }

    @Test
    void getStudentById_WhenStudentDoesNotExist_ThrowsNoEntityFoundException() {
        Mockito.when(studentRepository.findById(1)).thenReturn(Optional.empty());
        Assertions.assertThrows(NoEntityFoundException.class, () -> service.getStudentById(1));
        Mockito.verify(studentRepository, Mockito.times(1)).findById(1);
    }

    @Test
    void getStudentById_WhenStudentExists_ReturnsStudent() {
        Mockito.when(studentRepository.findById(1)).thenReturn(Optional.of(st1));
        StudentDto studentDto = studentMapper.entityToDto(st1);
        Assertions.assertEquals(studentDto, service.getStudentById(1));
        Mockito.verify(studentRepository, Mockito.times(1)).findById(1);
    }

    @Test
    void saveStudent_WhenStudentIsSaved_ReturnsStudentDto() {
        Mockito.when(studentRepository.save(Mockito.any(Student.class))).thenReturn(st1);
        StudentDto expectedDto = studentMapper.entityToDto(st1);
        StudentDto actualDto = service.saveStudent(studentMapper.entityToDto(st1));
        Assertions.assertEquals(expectedDto, actualDto);
        Mockito.verify(studentRepository, Mockito.times(1)).save(Mockito.any(Student.class));
    }


}
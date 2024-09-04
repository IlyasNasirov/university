package com.example.university.service;

import com.example.university.dto.StudentDto;
import com.example.university.entity.Student;
import com.example.university.exception.NoEntityFoundException;
import com.example.university.mapper.StudentMapper;
import com.example.university.repository.StudentRepository;
import org.checkerframework.checker.nullness.Opt;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceImplTest {

    @InjectMocks
    private StudentServiceImpl service;
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private StudentMapper studentMapper;

    private Student st1;
    private Student st2;
    private Student st3;

    @BeforeEach
    void init() {
        st1 = Student.builder().id(1).firstName("ilyas").lastName("nasirov").middleName("urakbayevich").age(25).build();
        st2 = Student.builder().id(2).firstName("ivan").lastName("ivanov").middleName("ivanovich").age(31).build();
        st3 = Student.builder().id(3).firstName("mixail").lastName("jekov").middleName("alekseivich").age(37).build();
    }

    @Test
    void getAllStudents_WhenCalled_ReturnsListOfStudentDtos() {
        List<Student> students = List.of(st1, st2, st3);
        when(studentRepository.findAll()).thenReturn(students);
        List<StudentDto> expectedDtos = students.stream()
                .map(studentMapper::entityToDto).collect(Collectors.toList());
        List<StudentDto> actualDtos = service.getAllStudents();
        assertEquals(expectedDtos, actualDtos);
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    void getStudentById_WhenStudentDoesNotExist_ShouldThrowNoEntityFoundException() {
        when(studentRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(NoEntityFoundException.class, () -> service.getStudentById(1));
        verify(studentRepository, times(1)).findById(1);
    }

    @Test
    void getStudentById_WhenStudentExists_ReturnsStudent() {
        when(studentRepository.findById(1)).thenReturn(Optional.of(st1));
        StudentDto studentDto = studentMapper.entityToDto(st1);
        assertEquals(studentDto, service.getStudentById(1));
        verify(studentRepository, times(1)).findById(1);
    }

    @Test
    void saveStudent_WhenStudentIsSaved_ReturnsStudentDto() {
        Student student = Student.builder().id(1).firstName("ivan").lastName("ivanov").middleName("ivanovich").age(30).build();
        StudentDto studentDto=studentMapper.entityToDto(student);

        when(studentMapper.dtoToEntity(studentDto)).thenReturn(student);
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentMapper.entityToDto(student)).thenReturn(studentDto);

        StudentDto actualDto = service.saveStudent(studentMapper.entityToDto(student));
        assertEquals(studentDto, actualDto);
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    void updateStudent_WhenStudentExists_ShouldUpdateStudent() {
        Student existingStudent = Student.builder().id(1).firstName("ivan").lastName("ivanov").middleName("ivanovich").age(30).build();
        StudentDto studentDto = StudentDto.builder().id(1).firstName("igor").lastName("ivanov").middleName("ivanovich").age(30).build();

        when(studentRepository.findById(existingStudent.getId())).thenReturn(Optional.of(existingStudent));
        when(studentRepository.save(existingStudent)).thenReturn(existingStudent);
        when(studentMapper.entityToDto(existingStudent)).thenReturn(studentDto);

        StudentDto updatedStudentDto = service.updateStudent(existingStudent.getId(), studentDto);
        assertEquals(studentDto, updatedStudentDto);

        verify(studentRepository, times(1)).save(existingStudent);
        verify(studentMapper, times(1)).entityToDto(existingStudent);

    }

    @Test
    void deleteStudent_WhenStudentExists_ShouldDeleteStudent() {
        Student student = Student.builder().id(9).firstName("igor").lastName("jekov").middleName("Mixailovich").age(16).build();
        when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));
        service.deleteStudent(student.getId());
        verify(studentRepository, times(1)).deleteById(student.getId());
    }

    @Test
    void deleteStudent_WhenStudentDoesNotExist_ShouldThrowNoEntityFoundException() {
        when(studentRepository.findById(st1.getId())).thenReturn(Optional.empty());
        NoEntityFoundException exception = assertThrows(NoEntityFoundException.class, () -> service.deleteStudent(st1.getId()));
        assertEquals("There is no student with id " + st1.getId(), exception.getMessage());
        verify(studentRepository, never()).deleteById(st1.getId());
    }


}

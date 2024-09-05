package com.example.university.service;

import com.example.university.dto.StudentDto;
import com.example.university.dto.SubjectDto;
import com.example.university.dto.TeacherDto;
import com.example.university.entity.Student;
import com.example.university.entity.Subject;
import com.example.university.entity.Teacher;
import com.example.university.exception.EntityAlreadyAddedException;
import com.example.university.exception.NoEntityFoundException;
import com.example.university.mapper.StudentMapper;
import com.example.university.mapper.SubjectMapper;
import com.example.university.mapper.TeacherMapper;
import com.example.university.repository.StudentRepository;

import com.example.university.repository.SubjectRepository;
import com.example.university.repository.TeacherRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
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
    private TeacherRepository teacherRepository;
    @Mock
    private SubjectRepository subjectRepository;
    @Mock
    private StudentMapper studentMapper;
    @Mock
    private TeacherMapper teacherMapper;
    @Mock
    private SubjectMapper subjectMapper;

    @Test
    void getAllStudents_WhenCalled_ReturnsListOfStudentDtos() {
        Student student1 = Student.builder().id(1).firstName("ilyas").lastName("nasirov").middleName("urakbayevich").age(25).build();
        Student student2 = Student.builder().id(2).firstName("ivan").lastName("ivanov").middleName("ivanovich").age(21).build();
        Student student3 = Student.builder().id(3).firstName("mixail").lastName("jekov").middleName("alekseivich").age(19).build();
        List<Student> students = List.of(student1, student2, student3);
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
        Student student1 = Student.builder().id(1).firstName("ilyas").lastName("nasirov").middleName("urakbayevich").age(25).build();
        when(studentRepository.findById(1)).thenReturn(Optional.of(student1));
        StudentDto studentDto = studentMapper.entityToDto(student1);
        assertEquals(studentDto, service.getStudentById(1));
        verify(studentRepository, times(1)).findById(1);
    }

    @Test
    void saveStudent_WhenStudentIsSaved_ReturnsStudentDto() {
        Student student = Student.builder().id(1).firstName("ivan").lastName("ivanov").middleName("ivanovich").age(30).build();
        StudentDto studentDto = studentMapper.entityToDto(student);

        when(studentMapper.dtoToEntity(studentDto)).thenReturn(student);
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentMapper.entityToDto(student)).thenReturn(studentDto);

        StudentDto actualDto = service.saveStudent(studentDto);
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
        Student st1 = Student.builder().id(1).firstName("ilyas").lastName("nasirov").middleName("urakbayevich").age(25).build();
        when(studentRepository.findById(st1.getId())).thenReturn(Optional.empty());
        NoEntityFoundException exception = assertThrows(NoEntityFoundException.class, () -> service.deleteStudent(st1.getId()));
        assertEquals("There is no student with id " + st1.getId(), exception.getMessage());
        verify(studentRepository, never()).deleteById(st1.getId());
    }

    @Test
    void addTeacherToStudent_WhenStudentDoesNotExist_ShouldThrownNoEntityFoundException() {
        when(studentRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(NoEntityFoundException.class, () -> service.addTeacherToStudent(1, 1));
    }

    @Test
    void addTeacherToStudent_WhenTeacherDoesNotExist_ShouldThrownNoEntityFoundException() {
        Student st1 = Student.builder().id(1).firstName("ilyas").lastName("nasirov").middleName("urakbayevich").age(25).build();
        when(studentRepository.findById(1)).thenReturn(Optional.of(st1));
        when(teacherRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(NoEntityFoundException.class, () -> service.addTeacherToStudent(1, 1));
        verify(studentRepository, times(1)).findById(1);
    }

    @Test
    void addTeacherToStudent_WhenStudentAlreadyHasTeacher_ShouldThrownEntityAlreadyAddedException() {
        Student student = Student.builder().id(1).firstName("ilyas").lastName("nasirov").middleName("urakbayevich").age(25).teachers(new ArrayList<>()).build();
        Teacher teacher = Teacher.builder().id(1).firstName("ivan").lastName("ivanov").middleName("ivanovich").age(31).build();
        when(studentRepository.findById(1)).thenReturn(Optional.of(student));
        when(teacherRepository.findById(1)).thenReturn(Optional.of(teacher));
        student.getTeachers().add(teacher);

        assertThrows(EntityAlreadyAddedException.class, () -> service.addTeacherToStudent(student.getId(), teacher.getId()));
        verify(studentRepository, never()).save(any(Student.class));
    }

    @Test
    void addTeacherToStudent_WhenTeacherDoesntExists_ShouldAddedTeacher() {
        Student student = Student.builder().id(1).firstName("ilyas").lastName("nasirov").middleName("urakbayevich").age(25).teachers(new ArrayList<>()).build();
        Teacher teacher = Teacher.builder().id(1).firstName("ivan").lastName("ivanov").middleName("ivanovich").age(31).students(new ArrayList<>()).build();
        when(studentRepository.findById(1)).thenReturn(Optional.of(student));
        when(teacherRepository.findById(1)).thenReturn(Optional.of(teacher));

        service.addTeacherToStudent(student.getId(), teacher.getId());
        verify(teacherRepository, times(1)).save(any(Teacher.class));
    }

    @Test
    void getAllTeachersOfStudent_WhenStudentHasTeachers_ShouldReturnAllTeachers() {
        Student student = Student.builder().id(1).firstName("ilyas").lastName("nasirov").middleName("urakbayevich").age(25).teachers(new ArrayList<>()).build();
        Teacher teacher = Teacher.builder().id(1).firstName("ivan").lastName("ivanov").middleName("ivanovich").age(31).students(new ArrayList<>()).build();
        student.getTeachers().add(teacher);

        when(studentRepository.findById(1)).thenReturn(Optional.of(student));
        List<TeacherDto> teacherDtos = student.getTeachers().stream().map(teacherMapper::entityToDto).collect(Collectors.toList());

        assertEquals(teacherDtos, service.getAllTeachersOfStudent(student.getId()));
        verify(studentRepository, times(1)).findById(student.getId());
    }

    @Test
    void getAllSubjectsOfStudent_WhenStudentHasSubjects_ShouldReturnAllSubjects() {
        Student student = Student.builder().id(1).firstName("ilyas").lastName("nasirov").middleName("urakbayevich").age(25).subjects(new ArrayList<>()).build();
        Subject subject = Subject.builder().id(1).name("Math").build();
        student.getSubjects().add(subject);

        when(studentRepository.findById(1)).thenReturn(Optional.of(student));
        List<SubjectDto> subjectDtos = student.getSubjects().stream().map(subjectMapper::entityToDto).collect(Collectors.toList());

        assertEquals(subjectDtos, service.getAllSubjectsOfStudent(student.getId()));
        verify(studentRepository, times(1)).findById(student.getId());
    }

    @Test
    void addSubjectToStudent_WhenStudentAlreadyHasSubject_ShouldThrownEntityAlreadyAddedException() {
        Student student = Student.builder().id(1).firstName("ilyas").lastName("nasirov").middleName("urakbayevich").age(25).subjects(new ArrayList<>()).build();
        Subject subject = Subject.builder().id(1).name("Math").build();
        when(studentRepository.findById(1)).thenReturn(Optional.of(student));
        when(subjectRepository.findById(1)).thenReturn(Optional.of(subject));
        student.getSubjects().add(subject);

        assertThrows(EntityAlreadyAddedException.class, () -> service.addSubjectToStudent(student.getId(), subject.getId()));
        verify(studentRepository, never()).save(any(Student.class));
    }

    @Test
    void addSubjectToStudent_WhenSubjectDoesntExists_ShouldAddedSubject() {
        Student student = Student.builder().id(1).firstName("ilyas").lastName("nasirov").middleName("urakbayevich").age(25).subjects(new ArrayList<>()).build();
        Subject subject = Subject.builder().id(1).name("Math").build();
        when(studentRepository.findById(1)).thenReturn(Optional.of(student));
        when(subjectRepository.findById(1)).thenReturn(Optional.of(subject));
        service.addSubjectToStudent(student.getId(), subject.getId());
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    void deleteSubjectFromStudent_WhenSubjectExists_ShouldDeleteSubject() {
        Student student = Student.builder().id(1).firstName("ilyas").lastName("nasirov").middleName("urakbayevich").age(25).subjects(new ArrayList<>()).build();
        Subject subject = Subject.builder().id(1).name("Math").build();
        student.getSubjects().add(subject);
        when(studentRepository.findById(1)).thenReturn(Optional.of(student));
        when(subjectRepository.findById(1)).thenReturn(Optional.of(subject));
        service.deleteSubjectFromStudent(student.getId(), subject.getId());
    }

    @Test
    void deleteTeacherFromStudent_WhenTeacherExists_ShouldDeleteTeacher() {
        Student student = Student.builder().id(1).firstName("ilyas").lastName("nasirov").middleName("urakbayevich").age(25).teachers(new ArrayList<>()).build();
        Teacher teacher = Teacher.builder().id(1).firstName("ivan").lastName("ivanov").middleName("ivanovich").age(31).students(new ArrayList<>()).build();
        student.getTeachers().add(teacher);
        when(studentRepository.findById(1)).thenReturn(Optional.of(student));
        when(teacherRepository.findById(1)).thenReturn(Optional.of(teacher));
        service.deleteTeacherFromStudent(student.getId(), teacher.getId());
    }
}

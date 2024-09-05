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
class TeacherServiceImplTest {

    @InjectMocks
    private TeacherServiceImpl service;
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
    void getAllTeachers_WhenCalled_ReturnsListTeacherDtos() {
        Teacher teacher1=Teacher.builder().id(1).firstName("ivan").lastName("ivanov").middleName("ivanovich").age(31).build();
        Teacher teacher2=Teacher.builder().id(2).firstName("zaur").lastName("tregulov").middleName("alekseivich").age(37).build();
        Teacher teacher3=Teacher.builder().id(3).firstName("aleksey").lastName("alekseiv").middleName("alekseivich").age(25).build();
        List<Teacher> teachers = List.of(teacher1, teacher2, teacher3);
        when(teacherRepository.findAll()).thenReturn(teachers);
        List<TeacherDto> expectedDtos = teachers.stream()
                .map(teacherMapper::entityToDto).collect(Collectors.toList());
        List<TeacherDto> actualDtos = service.getAllTeachers();
        assertEquals(expectedDtos, actualDtos);
        verify(teacherRepository, times(1)).findAll();
    }
    @Test
    void getTeacherById_WhenTeacherDoesNotExist_ShouldThrowNoEntityFoundException() {
        when(teacherRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(NoEntityFoundException.class, () -> service.getTeacherById(1));
        verify(teacherRepository, times(1)).findById(1);
    }

    @Test
    void getTeacherById_WhenTeacherExists_ReturnsTeacher() {
        Teacher teacher=Teacher.builder().id(1).firstName("ivan").lastName("ivanov").middleName("ivanovich").age(31).build();
        when(teacherRepository.findById(1)).thenReturn(Optional.of(teacher));
        TeacherDto teacherDto = teacherMapper.entityToDto(teacher);
        assertEquals(teacherDto, service.getTeacherById(1));
        verify(teacherRepository, times(1)).findById(1);
    }

    @Test
    void saveTeacher_WhenTeacherIsSaved_ReturnsTeacherDto() {
        Teacher teacher = Teacher.builder().id(1).firstName("ivan").lastName("ivanov").middleName("ivanovich").age(30).build();
        TeacherDto teacherDto = teacherMapper.entityToDto(teacher);

        when(teacherMapper.dtoToEntity(teacherDto)).thenReturn(teacher);
        when(teacherRepository.save(any(Teacher.class))).thenReturn(teacher);
        when(teacherMapper.entityToDto(teacher)).thenReturn(teacherDto);

        TeacherDto actualDto = service.saveTeacher(teacherDto);
        assertEquals(teacherDto, actualDto);
        verify(teacherRepository, times(1)).save(any(Teacher.class));
    }

    @Test
    void updateTeacher_WhenTeacherExists_ShouldUpdateTeacher() {
        Teacher existingTeacher = Teacher.builder().id(1).firstName("ivan").lastName("ivanov").middleName("ivanovich").age(30).build();
        TeacherDto teacherDto = TeacherDto.builder().id(1).firstName("igor").lastName("ivanov").middleName("ivanovich").age(30).build();

        when(teacherRepository.findById(existingTeacher.getId())).thenReturn(Optional.of(existingTeacher));
        when(teacherRepository.save(any(Teacher.class))).thenReturn(existingTeacher);
        when(teacherMapper.entityToDto(existingTeacher)).thenReturn(teacherDto);

        TeacherDto updatedTeacherDto = service.updateTeacher(existingTeacher.getId(),teacherDto);
        assertEquals(teacherDto, updatedTeacherDto);
        verify(teacherRepository, times(1)).save(any(Teacher.class));
        verify(teacherMapper, times(1)).entityToDto(existingTeacher);

    }

    @Test
    void deleteTeacher_WhenTeacherExists_ShouldDeleteTeacher() {
        Teacher teacher = Teacher.builder().id(1).firstName("igor").lastName("jekov").middleName("Mixailovich").age(31).build();
        when(teacherRepository.findById(teacher.getId())).thenReturn(Optional.of(teacher));
        service.deleteTeacher(teacher.getId());
        verify(teacherRepository, times(1)).deleteById(teacher.getId());
    }

    @Test
    void addSubjectToTeacher_WhenTeacherDoesNotExist_ShouldThrowNoEntityFoundException() {
        when(teacherRepository.findById(1)).thenReturn(Optional.empty());
        SubjectDto subjectDto = SubjectDto.builder().id(1).name("Math").build();
        assertThrows(NoEntityFoundException.class, () -> service.addSubjectToTeacher(1, subjectDto));
    }
    @Test
    void addSubjectToTeacher_WhenSubjectAlreadyExists_ShouldThrowEntityAlreadyAddedException() {
        Teacher teacher = Teacher.builder().id(1).firstName("igor").lastName("jekov").middleName("Mixailovich").age(31).subjects(new ArrayList<>()).build();
        Subject subject = Subject.builder().id(1).name("Math").build();
        SubjectDto subjectDto= subjectMapper.entityToDto(subject);
        teacher.getSubjects().add(subject);
        when(teacherRepository.findById(1)).thenReturn(Optional.of(teacher));
        when(subjectMapper.dtoToEntity(subjectDto)).thenReturn(subject);
        assertThrows(EntityAlreadyAddedException.class, () -> service.addSubjectToTeacher(teacher.getId(), subjectDto));
        verify(teacherRepository, never()).save(any(Teacher.class));
    }
    @Test
    void addSubjectToTeacher_WhenSubjectDoesntExists_ShouldAddedSubject() {
        Teacher teacher = Teacher.builder().id(1).firstName("igor").lastName("jekov").middleName("Mixailovich").age(31).subjects(new ArrayList<>()).build();
        Subject subject = Subject.builder().id(1).name("Math").build();
        SubjectDto subjectDto= subjectMapper.entityToDto(subject);
        when(teacherRepository.findById(1)).thenReturn(Optional.of(teacher));
        when(subjectMapper.dtoToEntity(subjectDto)).thenReturn(subject);
        service.addSubjectToTeacher(teacher.getId(), subjectDto);
        verify(teacherRepository, times(1)).save(any(Teacher.class));
    }
    @Test
    void getAllSubjectsOfTeacher_WhenTeacherHasSubjects_ShouldReturnAllSubjects() {
        Teacher teacher = Teacher.builder().id(1).firstName("igor").lastName("jekov").middleName("Mixailovich").age(31).subjects(new ArrayList<>()).build();
        Subject subject = Subject.builder().id(1).name("Math").build();
        teacher.getSubjects().add(subject);

        when(teacherRepository.findById(1)).thenReturn(Optional.of(teacher));
        List<SubjectDto> subjectDtos = teacher.getSubjects().stream().map(subjectMapper::entityToDto).collect(Collectors.toList());

        assertEquals(subjectDtos, service.getAllSubjectsOfTeacher(teacher.getId()));
        verify(teacherRepository, times(1)).findById(teacher.getId());
    }

    @Test
    void deleteSubjectFromTeacher_WhenSubjectExists_ShouldDeleteSubject() {
        Teacher teacher = Teacher.builder().id(1).firstName("igor").lastName("jekov").middleName("Mixailovich").age(31).subjects(new ArrayList<>()).build();
        Subject subject = Subject.builder().id(1).name("Math").build();
        teacher.getSubjects().add(subject);
        when(teacherRepository.findById(1)).thenReturn(Optional.of(teacher));
        when(subjectRepository.findById(1)).thenReturn(Optional.of(subject));
        service.deleteSubjectOfTeacher(teacher.getId(), subject.getId());
        assertEquals(0, teacher.getSubjects().size());
    }

    @Test
    void getAllStudentsOfTeacher_WhenTeacherHasStudents_ShouldReturnAllStudents() {
        Teacher teacher = Teacher.builder().id(1).firstName("igor").lastName("jekov").middleName("Mixailovich").age(31).students(new ArrayList<>()).build();
        Student student = Student.builder().id(1).firstName("ilyas").lastName("nasirov").middleName("urakbayevich").age(25).teachers(new ArrayList<>()).build();
        teacher.getStudents().add(student);

        when(teacherRepository.findById(1)).thenReturn(Optional.of(teacher));
        List<StudentDto> studentDtos = teacher.getStudents().stream().map(studentMapper::entityToDto).collect(Collectors.toList());

        assertEquals(studentDtos, service.getAllStudentsOfTeacher(teacher.getId()));
        verify(teacherRepository, times(1)).findById(teacher.getId());
    }

    @Test
    void deleteStudentOfTeacher_WhenStudentExists_ShouldDeleteStudent() {
        Teacher teacher = Teacher.builder().id(1).firstName("igor").lastName("jekov").middleName("Mixailovich").age(31).students(new ArrayList<>()).build();
        Student student = Student.builder().id(1).firstName("ilyas").lastName("nasirov").middleName("urakbayevich").age(25).teachers(new ArrayList<>()).build();
        teacher.getStudents().add(student);

        when(teacherRepository.findById(1)).thenReturn(Optional.of(teacher));
        when(studentRepository.findById(1)).thenReturn(Optional.of(student));
        service.deleteStudentOfTeacher(teacher.getId(), student.getId());

        verify(teacherRepository).save(teacher);
    }

    @Test
    void addStudentToTeacher_WhenStudentDoesntExists_ShouldAddedStudent() {
        Teacher teacher = Teacher.builder().id(1).firstName("igor").lastName("jekov").middleName("Mixailovich").age(31).students(new ArrayList<>()).build();
        Student student = Student.builder().id(1).firstName("ilyas").lastName("nasirov").middleName("urakbayevich").age(25).teachers(new ArrayList<>()).build();
        when(teacherRepository.findById(1)).thenReturn(Optional.of(teacher));
        when(studentRepository.findById(1)).thenReturn(Optional.of(student));
        service.addStudentToTeacher(teacher.getId(), student.getId());
        verify(teacherRepository, times(1)).save(teacher);
    }


}
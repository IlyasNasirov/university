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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentRepository studentRepo;
    @Autowired
    TeacherRepository teacherRepo;
    @Autowired
    SubjectRepository subjectRepo;

    private final StudentMapper studentMapper = StudentMapper.INSTANCE;
    private final SubjectMapper subjectMapper = SubjectMapper.INSTANCE;
    private final TeacherMapper teacherMapper = TeacherMapper.INSTANCE;

    @Override
    public StudentDto getStudentById(int id) {
        Student student = studentRepo.findById(id)
                .orElseThrow(() -> new NoEntityFoundException("There is no student with id " + id));
        return studentMapper.entityToDto(student);
    }

    @Override
    public List<StudentDto> getAllStudents() {
        List<Student> list = studentRepo.findAll();
        return list.stream()
                .map(studentMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public StudentDto saveStudent(StudentDto studentDto) {  //здесь обрабатывается тип исключение handleValidationExceptions
        Student student = studentMapper.dtoToEntity(studentDto);
        studentRepo.save(student);
        return studentMapper.entityToDto(student);
    }

    @Override
    public StudentDto updateStudent(int id, StudentDto studentDto) {
        Student student = studentRepo.findById(id)
                .orElseThrow(() -> new NoEntityFoundException("There is no student with id " + id));
        student.setFirstName(studentDto.getFirstName());
        student.setLastName(studentDto.getLastName());
        student.setMiddleName(studentDto.getMiddleName());
        student.setAge(studentDto.getAge());
        studentRepo.save(student);
        return studentMapper.entityToDto(student);
    }

    @Override
    public void deleteStudent(int id) {
        studentRepo.findById(id).orElseThrow(() -> new NoEntityFoundException("There is no student with id " + id));
        studentRepo.deleteById(id);
    }

    @Override
    public void addTeacherToStudent(int studentId, int teacherId) {
        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new NoEntityFoundException("There is no student with id " + studentId));
        Teacher teacher = teacherRepo.findById(teacherId)
                .orElseThrow(() -> new NoEntityFoundException("There is no teacher with id " + teacherId));
        if (student.getTeachers().contains(teacher)) {
            throw new EntityAlreadyAddedException("There is already a teacher with id " + teacherId);
        }
        student.getTeachers().add(teacher);
        teacher.getStudents().add(student);
        studentRepo.save(student);
        teacherRepo.save(teacher);  //надо ли выполнять это действия?
    }

    @Override
    public List<TeacherDto> getAllTeachersOfStudent(int studentId) {
        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new NoEntityFoundException("There is no student with id " + studentId));
        List<Teacher> teachers = student.getTeachers();
        return teachers.stream().map(teacherMapper::entityToDto).collect(Collectors.toList());
    }

    //нужно получать только свои предметы из учителей
    @Override
    public List<SubjectDto> getAllSubjectsOfStudent(int studentId) {
        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new NoEntityFoundException("There is no student with id " + studentId));
        List<Subject> subjects = student.getSubjects();
        return subjects.stream().map(subjectMapper::entityToDto).collect(Collectors.toList());
    }

    @Override
    public void addSubjectToStudent(int studentId, int subjectId) {
        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new NoEntityFoundException("There is no student with id " + studentId));
        Subject subject = subjectRepo.findById(subjectId)
                .orElseThrow(() -> new NoEntityFoundException("There is no subject with id " + subjectId));
        if (student.getSubjects().contains(subject))
            throw new EntityAlreadyAddedException("There is already a subject with id " + subjectId);
        student.getSubjects().add(subject);
        subject.getStudents().add(student);
        studentRepo.save(student);
    }

    @Override
    public void deleteSubjectFromStudent(int studentId, int subjectId) {
        Student student=studentRepo.findById(studentId)
                .orElseThrow(() -> new NoEntityFoundException("There is no student with id " + studentId));
        Subject subject = subjectRepo.findById(subjectId)
                .orElseThrow(() -> new NoEntityFoundException("There is no subject with id " + subjectId));
        student.getSubjects().remove(subject);
        studentRepo.save(student);
    }

    @Override
    public void deleteTeacherFromStudent(int studentId, int teacherId) {
        Student student=studentRepo.findById(studentId)
                .orElseThrow(() -> new NoEntityFoundException("There is no student with id " + studentId));
        Teacher teacher = teacherRepo.findById(teacherId)
                .orElseThrow(() -> new NoEntityFoundException("There is no teacher with id " + teacherId));
        student.getTeachers().remove(teacher);
        teacher.getStudents().remove(student);
        studentRepo.save(student);
        teacherRepo.save(teacher);
    }


}
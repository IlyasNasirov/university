package com.example.university.service;

import com.example.university.dto.StudentDto;
import com.example.university.dto.SubjectDto;
import com.example.university.dto.TeacherDto;
import com.example.university.entity.Student;
import com.example.university.entity.Subject;
import com.example.university.entity.Teacher;
import com.example.university.mapper.StudentMapper;
import com.example.university.mapper.SubjectMapper;
import com.example.university.mapper.TeacherMapper;
import com.example.university.repository.StudentRepository;
import com.example.university.repository.SubjectRepository;
import com.example.university.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    @Override
    public StudentDto getStudentById(int id) {
        Optional<Student> optional = studentRepo.findById(id);
        return optional.map(studentMapper::entityToDto).orElse(null);
    }

    @Override
    public List<StudentDto> getAllStudents() {
        List<Student> list = studentRepo.findAll();
        return list.stream()
                .map(studentMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public StudentDto saveStudent(StudentDto stDto) {
        Student student = studentMapper.dtoToEntity(stDto);
        studentRepo.save(student);
        return studentMapper.entityToDto(student);
    }

    @Override
    public StudentDto updateStudent(int id, StudentDto studentDto) {
        Student student = studentRepo.findById(id).orElseThrow(() -> new RuntimeException("Student is not exist"));
        student.setFirstName(studentDto.getFirstName());
        student.setLastName(studentDto.getLastName());
        student.setMiddleName(studentDto.getMiddleName());
        student.setAge(studentDto.getAge());
        studentRepo.save(student);
        return studentMapper.entityToDto(student);
    }

    @Override
    public void deleteStudent(int id) {
        studentRepo.deleteById(id);
    }

    @Override
    public void setTeacherForStudent(int studentId, int teacherId) {
        Student student = studentRepo.findById(studentId).orElseThrow(() -> new RuntimeException("Student does not exist"));
        Teacher teacher = teacherRepo.findById(teacherId).orElseThrow(() -> new RuntimeException("Teacher does not exist"));
        if (student.getTeachers().contains(teacher)) {
            throw new RuntimeException("Teacher has already been added to the student");
        }
        student.getTeachers().add(teacher);
        teacher.getStudents().add(student);
        studentRepo.save(student);
    }

    @Override
    public List<TeacherDto> getAllTeachersOfStudent(int studentId) {
        Student student = studentRepo.findById(studentId).orElseThrow(() -> new RuntimeException("Student is not exist"));
        List<Teacher> teachers = student.getTeachers();
        return teachers.stream().map(TeacherMapper.INSTANCE::entityToDto).collect(Collectors.toList());
    }

    //нужно получать свои предметы из учителей
    @Override
    public List<SubjectDto> getAllSubjectsOfStudent(int studentId) {
        Student student = studentRepo.findById(studentId).orElseThrow(() -> new RuntimeException("Student does not exist"));
        List<Subject> subjects=student.getSubjects();
        return subjects.stream().map(subjectMapper::entityToDto).collect(Collectors.toList());
    }

    @Override
    public void setSubjectForStudent(int studentId, int subjectId) {
        Student student = studentRepo.findById(studentId).orElseThrow(() -> new RuntimeException("Student does not exist"));
        Subject subject = subjectRepo.findById(subjectId).orElseThrow(() -> new RuntimeException("Subject does not exist"));
        if(student.getSubjects().contains(subject))
            throw new RuntimeException("Subject has already been added to the student");
        student.getSubjects().add(subject);
        subject.getStudents().add(student);
        studentRepo.save(student);
    }
}

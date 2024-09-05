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
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private TeacherRepository teacherRepo;
    private SubjectRepository subjectRepo;
    private StudentRepository studentRepo;
    private TeacherMapper teacherMapper;
    private SubjectMapper subjectMapper;
    private StudentMapper studentMapper;

    @Override
    public List<TeacherDto> getAllTeachers() {
        List<Teacher> list = teacherRepo.findAll();
        return list.stream()
                .map(teacherMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public TeacherDto getTeacherById(int id) {
        Teacher teacher = teacherRepo.findById(id)
                .orElseThrow(() -> new NoEntityFoundException("No teacher found with id " + id));
        return teacherMapper.entityToDto(teacher);
    }

    @Override
    public TeacherDto saveTeacher(TeacherDto teacherDto) {  //здесь обрабатывается тип исключение handleValidationExceptions
        Teacher teacher = teacherMapper.dtoToEntity(teacherDto);
        teacherRepo.save(teacher);
        return teacherMapper.entityToDto(teacher);
    }

    @Override
    public TeacherDto updateTeacher(int teacherId, TeacherDto dto) {
        Teacher teacher = teacherRepo.findById(teacherId)
                .orElseThrow(() -> new NoEntityFoundException("Teacher is not found with id " + teacherId));
        teacher.setFirstName(dto.getFirstName());
        teacher.setLastName(dto.getLastName());
        teacher.setMiddleName(dto.getMiddleName());
        teacher.setAge(dto.getAge());
        teacherRepo.save(teacher);
        return teacherMapper.entityToDto(teacher);
    }

    @Override
    public void deleteTeacher(int id) {
        teacherRepo.findById(id).orElseThrow(() -> new NoEntityFoundException("There is no teacher with id " + id));
        teacherRepo.deleteById(id);
    }

    @Override
    public void addSubjectToTeacher(int teacherId, SubjectDto subjectDto) { //здесь обрабатывается тип исключение handleValidationExceptions
        Teacher teacher = teacherRepo.findById(teacherId)
                .orElseThrow(() -> new NoEntityFoundException("Teacher is not found with id " + teacherId));
        Subject subject = subjectMapper.dtoToEntity(subjectDto);
        if (teacher.getSubjects().stream().anyMatch(sub -> sub.getName().equalsIgnoreCase(subject.getName()))) {
            throw new EntityAlreadyAddedException("Teacher already teaches the subject with name " + subject.getName());
        }
        teacher.getSubjects().add(subject);
        subject.setTeacher(teacher);
        teacherRepo.save(teacher);
    }

    @Override
    public List<SubjectDto> getAllSubjectsOfTeacher(int id) {
        Teacher teacher = teacherRepo.findById(id)
                .orElseThrow(() -> new NoEntityFoundException("There is no teacher with id " + id));
        List<Subject> subjects = teacher.getSubjects();
        return subjects.stream().map(subjectMapper::entityToDto).collect(Collectors.toList());
    }

    @Override
    public void deleteSubjectOfTeacher(int teacherId, int subjectId) {
        Teacher teacher = teacherRepo.findById(teacherId)
                .orElseThrow(() -> new NoEntityFoundException("Teacher is not found with id " + teacherId));
        Subject subject = subjectRepo.findById(subjectId)
                .orElseThrow(() -> new NoEntityFoundException("Subject is not found with id " + subjectId));
        teacher.getSubjects().remove(subject);
        //check this
        teacherRepo.save(teacher);
    }

    @Override
    public List<StudentDto> getAllStudentsOfTeacher(int teacherId) {
        Teacher teacher = teacherRepo.findById(teacherId)
                .orElseThrow(() -> new NoEntityFoundException("Teacher is not found with id " + teacherId));
        List<Student> students = teacher.getStudents();
        return students.stream().map(studentMapper::entityToDto).collect(Collectors.toList());
    }

    @Override
    public void deleteStudentOfTeacher(int teacherId, int studentId) {
        Teacher teacher = teacherRepo.findById(teacherId)
                .orElseThrow(() -> new NoEntityFoundException("Teacher is not found with id " + teacherId));
        teacher.getSubjects().remove(studentId);
    }

    @Override
    public void addStudentToTeacher(int teacherId, int studentId) {
        Teacher teacher = teacherRepo.findById(teacherId)
                .orElseThrow(() -> new NoEntityFoundException("Teacher is not found with id " + teacherId));
        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new NoEntityFoundException("Student is not found with id " + studentId));
        teacher.getStudents().add(student);
        teacherRepo.save(teacher);
    }

}

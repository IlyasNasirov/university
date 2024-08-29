package com.example.university.service;

import com.example.university.dto.StudentDto;
import com.example.university.dto.TeacherDto;
import com.example.university.entity.Student;
import com.example.university.entity.Teacher;
import com.example.university.mapper.StudentMapper;
import com.example.university.mapper.TeacherMapper;
import com.example.university.repository.StudentRepository;
import com.example.university.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentRepository studentRepo;
    @Autowired
    TeacherRepository teacherRepo;
    private final StudentMapper studentMapper = StudentMapper.INSTANCE;

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
        Student student=studentRepo.findById(id).orElseThrow(()->new RuntimeException("Student is not exist"));
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
    public void setTeacherForStudent(int studentId, int TeacherId) {
        Student student=studentRepo.findById(studentId).orElseThrow(()->new RuntimeException("Student is not exist"));
        Teacher teacher=teacherRepo.findById(studentId).orElseThrow(()->new RuntimeException("Teacher is not exist"));
        student.getTeachers().add(teacher);
        teacher.getStudents().add(student);
        studentRepo.save(student);
    }

    @Override
    public List<TeacherDto> getAllTeachersOfStudent(int studentId) {
        Student student=studentRepo.findById(studentId).orElseThrow(()->new RuntimeException("Student is not exist"));
        List<Teacher> teachers=student.getTeachers();
        return teachers.stream().map(TeacherMapper.INSTANCE::entityToDto).collect(Collectors.toList());
    }

    //    public StudentDto setTeacher(int id,int teacherId){
//        Student student;
//        Teacher teacher;
//        Optional<Student> optionalSt=studentRepo.findById(id);
//        if(optionalSt.isPresent()){
//            student=optionalSt.get();
//        }
//    Optional<Teacher> optionalTeach=teacherRepo.findById(teacherId);
//        if(optionalTeach.isPresent()){
//             teacher=optionalTeach.get();
//            String teacherName=teacher.getFirstName()+" "+teacher.getMiddleName();
//        }
//
//    }

}

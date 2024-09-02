package com.example.university.controller;

import com.example.university.dto.StudentDto;
import com.example.university.dto.SubjectDto;
import com.example.university.dto.TeacherDto;
import com.example.university.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    StudentService service;

    @GetMapping
    public ResponseEntity<List<StudentDto>> getAllStudents() {
        List<StudentDto> list = service.getAllStudents();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> getStudentById(@PathVariable int id) {
        StudentDto studentDto = service.getStudentById(id);
        return new ResponseEntity<>(studentDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<StudentDto> saveStudent(@Valid @RequestBody StudentDto studentDto) {
        StudentDto dto = service.saveStudent(studentDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDto> updateStudent(@PathVariable int id, @RequestBody StudentDto studentDto) {
        StudentDto dto = service.updateStudent(id, studentDto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudentById(@PathVariable int id) {
        service.deleteStudent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}/teachers")
    public ResponseEntity<Void> addTeacherToStudent(@PathVariable int id, @RequestParam int setTeacher) {
        service.addTeacherToStudent(id, setTeacher);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}/teachers")
    public ResponseEntity<List<TeacherDto>> getAllTeachersOfStudent(@PathVariable int id) {
        List<TeacherDto> teachersDto = service.getAllTeachersOfStudent(id);
        return new ResponseEntity<>(teachersDto, HttpStatus.OK);
    }
    @DeleteMapping("/{id}/teachers")
    public ResponseEntity<Void> deleteTeacherFromStudent(@PathVariable int id, @RequestParam int setTeacher) {
        service.deleteTeacherFromStudent(id, setTeacher);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}/subjects")
    public ResponseEntity<Void> addSubjectToStudent(@PathVariable int id, @RequestParam int setSubject) {
        service.addSubjectToStudent(id, setSubject);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}/subjects")
    public ResponseEntity<List<SubjectDto>> getAllSubjectsOfStudent(@PathVariable int id) {
        List<SubjectDto> subjectsDto = service.getAllSubjectsOfStudent(id);
        return new ResponseEntity<>(subjectsDto, HttpStatus.OK);
    }
    @DeleteMapping("/{id}/subjects")
    public ResponseEntity<Void> deleteSubjectFromStudent(@PathVariable int id, @RequestParam int setSubject) {
        service.deleteSubjectFromStudent(id, setSubject);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}

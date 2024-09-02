package com.example.university.controller;

import com.example.university.dto.StudentDto;
import com.example.university.dto.SubjectDto;
import com.example.university.dto.TeacherDto;
import com.example.university.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/teachers")
public class TeacherController {

    @Autowired
    TeacherService service;

    @GetMapping
    public ResponseEntity<List<TeacherDto>> getAllTeachers() {
        List<TeacherDto> list = service.getAllTeachers();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherDto> getTeacherByIdOrName(@PathVariable int id) {
        TeacherDto teacherDto = service.getTeacherById(id);
        return new ResponseEntity<>(teacherDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TeacherDto> saveTeacher(@Valid @RequestBody TeacherDto teacherDto) {
        TeacherDto dto = service.saveTeacher(teacherDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeacherDto> updateTeacher(@PathVariable int id, @RequestBody TeacherDto teacherDto) {
        TeacherDto dto = service.updateTeacher(id, teacherDto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeacherById(@PathVariable int id) {
        service.deleteTeacher(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}/subjects")
    public ResponseEntity<Void> addSubjectToTeacher(@PathVariable int id,@Valid @RequestBody SubjectDto dto) {
        service.addSubjectToTeacher(id, dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}/subjects")
    public ResponseEntity<List<SubjectDto>> getAllSubjectsOfTeacher(@PathVariable int id) {
        List<SubjectDto> teachersDto = service.getAllSubjectsOfTeacher(id);
        return new ResponseEntity<>(teachersDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/subjects")
    public ResponseEntity<Void> deleteSubjectOfTeacher(@PathVariable int id, @RequestParam int subjectId) {
        service.deleteSubjectOfTeacher(id, subjectId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}/students")
    public ResponseEntity<List<StudentDto>> getAllStudentsOfTeacher(@PathVariable int id) {
        List<StudentDto> studentsDto = service.getAllStudentsOfTeacher(id);
        return new ResponseEntity<>(studentsDto, HttpStatus.OK);
    }
    @PutMapping("/{id}/students")
    public ResponseEntity<Void> addStudentToTeacher(@PathVariable int id, @RequestParam int studentId) {
        service.addStudentToTeacher(id, studentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/{id}/students")
    public ResponseEntity<Void> deleteStudentOfTeacher(@PathVariable int id, @RequestParam int studentId) {
        service.deleteStudentOfTeacher(id, studentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

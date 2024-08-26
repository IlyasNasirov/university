package com.example.university.controller;

import com.example.university.dto.StudentDto;
import com.example.university.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    StudentService service;

    @GetMapping
    public ResponseEntity<List<StudentDto>> getAllStudents(){
        List<StudentDto> list= service.getAllStudents();
        return list!=null ? ResponseEntity.ok(list):ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> getStById(@PathVariable int id){
        StudentDto studentDto= service.getStudentById(id);
        return studentDto !=null ? ResponseEntity.ok(studentDto):ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> saveStudent(@RequestBody StudentDto studentDto){
        if(studentDto.getFirstName()==null || studentDto.getLastName()==null || studentDto.getMiddleName()==null || studentDto.getAge()==0)
            return ResponseEntity.badRequest().body("All fields must be filled in");
        StudentDto dto= service.saveStudent(studentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }
    @DeleteMapping("/{id}")
    public String deleteStById(@PathVariable int id){
        service.deleteStudent(id);
        return "Successful";
    }


}

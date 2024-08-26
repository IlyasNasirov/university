package com.example.university.controller;

import com.example.university.dto.StudentDto;
import com.example.university.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    StudentService service;

    @GetMapping
    public List<StudentDto> getAllStudents(){
        return service.getAllStudents();
    }

    @GetMapping("/{id}")
    public StudentDto getStById(@PathVariable int id){
        return service.getStudentById(id);
    }

    @PostMapping
    public StudentDto saveSt(@RequestBody StudentDto studentDto){
        return service.saveStudent(studentDto);
    }
    @DeleteMapping("/{id}")
    public String deleteStById(@PathVariable int id){
        service.deleteStudent(id);
        return "Successful";
    }


}

package com.example.university.controller;

import com.example.university.dto.TeacherDto;
import com.example.university.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/teachers")
public class TeacherController {

    @Autowired
    TeacherService service;

    @GetMapping
    public ResponseEntity<List<TeacherDto>> getAllTeachers(){
        List<TeacherDto> list= service.getAllTeachers();
        return !list.isEmpty() ? ResponseEntity.ok(list):ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<TeacherDto> getTeacherById(@PathVariable int id){
        TeacherDto teacherDto= service.getTeacherById(id);
        return teacherDto !=null ? ResponseEntity.ok(teacherDto):ResponseEntity.notFound().build();
    }
    @PostMapping
    public ResponseEntity<?> saveTeacher(@RequestBody TeacherDto teacherDto){
        if(teacherDto.getFirstName()==null || teacherDto.getLastName()==null || teacherDto.getMiddleName()==null || teacherDto.getAge()==0)
            return ResponseEntity.badRequest().body("All fields must be filled in");
        TeacherDto dto= service.saveTeacher(teacherDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }
    @DeleteMapping("/{id}")
    public String deleteTeacherById(@PathVariable int id){
        service.deleteTeacher(id);
        return "Successful";
    }


}

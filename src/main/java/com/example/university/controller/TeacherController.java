package com.example.university.controller;

import com.example.university.dto.SubjectDto;
import com.example.university.dto.TeacherDto;
import com.example.university.entity.Subject;
import com.example.university.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

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
    //TODO
    @GetMapping("/{input}")
    public ResponseEntity<TeacherDto> findByIdOrName(@PathVariable String input){
        TeacherDto teacherDto= service.findByIdOrName(input);
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
        System.out.println("  ");
        service.deleteTeacher(id);
        return "Successful";
    }
    @PutMapping("/{id}/subjects")  //add subject to Teacher
    public ResponseEntity<Void> addSubjectToTeacher(@PathVariable int id,@RequestBody Subject subject){
        try{
            service.addSubject(id,subject);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/{id}/subjects")  //get all subjects for teacher
    public ResponseEntity<Set<SubjectDto>> getAllSubjectForTeacher(@PathVariable int id){
        Set<SubjectDto> subjectDtos=service.getAllSubjectsForTeacher(id);
        return subjectDtos!=null ? ResponseEntity.ok(subjectDtos):ResponseEntity.notFound().build();
    }


}

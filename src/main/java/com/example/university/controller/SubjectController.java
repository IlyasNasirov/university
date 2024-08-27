package com.example.university.controller;

import com.example.university.dto.StudentDto;
import com.example.university.dto.SubjectDto;
import com.example.university.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {

    @Autowired
    SubjectService service;

    @GetMapping
    public ResponseEntity<List<SubjectDto>> getAllSubject(){
        List<SubjectDto> list= service.getAllSubjects();
        return list!=null ? ResponseEntity.ok(list):ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubjectDto> getSubjectById(@PathVariable int id){
        SubjectDto subjectDto= service.getSubjectById(id);
        return subjectDto !=null ? ResponseEntity.ok(subjectDto):ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> saveSubject(@RequestBody SubjectDto subjectDto){
        if(subjectDto.getName()==null)
            return ResponseEntity.badRequest().body("Name must be filled in");
        SubjectDto dto= service.saveSubject(subjectDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }
    @DeleteMapping("/{id}")
    public String deleteSubjectById(@PathVariable int id){
        service.deleteSubject(id);
        return "Successful";
    }

}

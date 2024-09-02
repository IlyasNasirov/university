package com.example.university.controller;

import com.example.university.dto.StudentDto;
import com.example.university.dto.SubjectDto;
import com.example.university.entity.Subject;
import com.example.university.service.SubjectService;
import com.example.university.service.SubjectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {

    @Autowired
    SubjectService service;

    @GetMapping
    public ResponseEntity<List<SubjectDto>> getAllSubject(){
        List<SubjectDto> list= service.getAllSubjects();
        return new ResponseEntity<>(list,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubjectDto> getSubjectById(@PathVariable int id){
        SubjectDto subjectDto= service.getSubjectById(id);
        return new ResponseEntity<>(subjectDto,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> saveSubject(@Valid @RequestBody SubjectDto subjectDto){
        SubjectDto dto= service.saveSubject(subjectDto);
        return new ResponseEntity<>(dto,HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubjectById(@PathVariable int id){
        service.deleteSubject(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubjectDto> updateSubject(@PathVariable int id, @RequestBody SubjectDto subjectDto){
       SubjectDto dto= service.updateSubject(id, subjectDto);
       return new ResponseEntity<>(dto,HttpStatus.OK);
    }

}

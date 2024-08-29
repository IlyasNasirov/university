package com.example.university.controller;

import com.example.university.dto.StudentDto;
import com.example.university.dto.SubjectDto;
import com.example.university.dto.TeacherDto;
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
    public ResponseEntity<StudentDto> getStudentById(@PathVariable int id){
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
    @PutMapping("/{id}")
    public ResponseEntity<StudentDto> updateStudent(@PathVariable int id,@RequestBody StudentDto studentDto){
        try{
            service.updateStudent(id,studentDto);
            return ResponseEntity.ok(studentDto);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @DeleteMapping("/{id}")
    public String deleteStudentById(@PathVariable int id){
        service.deleteStudent(id);
        return "Successful";
    }
    @PutMapping("/{id}/teachers")
    public ResponseEntity<String> addTeacherForStudent(@PathVariable int id, @RequestParam int setTeacher) {
        try {
            service.setTeacherForStudent(id, setTeacher);
            return ResponseEntity.ok().body("Added teacher to student");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Cannot add teacher to student");
        }
    }

    @GetMapping("/{id}/teachers")
    public ResponseEntity<List<TeacherDto>> getAllTeachersForStudents(@PathVariable int id){
        try{
           return ResponseEntity.ok(service.getAllTeachersOfStudent(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}

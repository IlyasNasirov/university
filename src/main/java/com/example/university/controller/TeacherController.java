package com.example.university.controller;

import com.example.university.dto.SubjectDto;
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
    public ResponseEntity<List<TeacherDto>> getAllTeachers() {
        List<TeacherDto> list = service.getAllTeachers();
        return !list.isEmpty() ? ResponseEntity.ok(list) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{input}")
    public ResponseEntity<TeacherDto> findByIdOrName(@PathVariable String input) {
        TeacherDto teacherDto = service.getTeacherByIdOrName(input);
        return teacherDto != null ? ResponseEntity.ok(teacherDto) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> saveTeacher(@RequestBody TeacherDto teacherDto) {
        if (teacherDto.getFirstName() == null || teacherDto.getLastName() == null || teacherDto.getMiddleName() == null || teacherDto.getAge() == 0)
            return ResponseEntity.badRequest().body("All fields must be filled in");
        TeacherDto dto = service.saveTeacher(teacherDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @DeleteMapping("/{id}")
    public String deleteTeacherById(@PathVariable int id) {
        service.deleteTeacher(id);
        return "Successful";
    }
    @PutMapping("/{id}")
    public ResponseEntity<TeacherDto> updateTeacher(@PathVariable int id,@RequestBody TeacherDto teacherDto){
        try{
            service.updateTeacher(id,teacherDto);
            return ResponseEntity.ok(teacherDto);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @PutMapping("/{id}/subjects")  //add subject to Teacher
        public ResponseEntity<String> addSubjectToTeacher(@PathVariable int id, @RequestBody SubjectDto dto) {
            try {
                service.addSubject(id, dto);
                return ResponseEntity.ok().body("Added subject to teacher");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Cannot add subject to teacher");
            }
        }

    @GetMapping("/{id}/subjects")  //get all subjects for teacher
    public ResponseEntity<List<SubjectDto>> getAllSubjectForTeacher(@PathVariable int id) {
        List<SubjectDto> subjectDtos = service.getAllSubjectsForTeacher(id);
        return subjectDtos != null ? ResponseEntity.ok(subjectDtos) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}/subjects")
    public ResponseEntity<String> deleteSubjectOfTeacher(@PathVariable int id, @RequestParam int subjectId) {
        try {
            service.deleteSubject(id, subjectId);
            return ResponseEntity.ok().body("Successfully deleted subject from teacher with"+ id);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Cannot delete");
        }
    }


}

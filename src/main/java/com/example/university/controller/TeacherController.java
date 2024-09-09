package com.example.university.controller;

import com.example.university.dto.StudentDto;
import com.example.university.dto.SubjectDto;
import com.example.university.dto.TeacherDto;
import com.example.university.service.TeacherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * REST controller for managing {@link com.example.university.entity.Teacher} entities.
 *
 * <p>This controller handles HTTP requests related to teacher operations, such as creating,
 * reading, updating, and deleting teacher data. It is mapped to the {@code /api/teachers} endpoint.
 *
 * <p>The {@code @RestController} annotation indicates that this class is a REST controller.
 * The {@code @Tag} annotation is used for OpenAPI documentation, tagging this controller
 * with the name "Teacher".
 */
@Tag(name = "Teacher")
@RestController
@RequestMapping("api/teachers")
public class TeacherController {

    @Autowired
    TeacherService service;

    @Operation(summary = "Get all teachers", description = "Returns a list of teachers")
    @ApiResponse(responseCode = "200", description = "List of teachers",
            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = TeacherDto.class))))
    @GetMapping
    public ResponseEntity<List<TeacherDto>> getAllTeachers() {
        List<TeacherDto> list = service.getAllTeachers();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @Operation(summary = "Get teacher by id", description = "Returns a teacher by id. If there is no teacher with such id, returns 404.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Teacher found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TeacherDto.class))),
            @ApiResponse(responseCode = "404", description = "Teacher not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<TeacherDto> getTeacherByIdOrName(@Parameter(description = "Id of the teacher") @PathVariable int id) {
        TeacherDto teacherDto = service.getTeacherById(id);
        return new ResponseEntity<>(teacherDto, HttpStatus.OK);
    }

    @Operation(summary = "Create new teacher", description = "Creates a new teacher")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Teacher created",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TeacherDto.class))),
            @ApiResponse(responseCode = "400", description = "Validation failed", content = @Content)
    })
    @PostMapping
    public ResponseEntity<TeacherDto> saveTeacher(@Valid @RequestBody TeacherDto teacherDto) {
        TeacherDto dto = service.saveTeacher(teacherDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @Operation(summary = "Update teacher by id", description = "Updates a teacher by id. If there is no teacher with such id, returns 404.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Teacher updated",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TeacherDto.class))),
            @ApiResponse(responseCode = "400", description = "Validation failed", content = @Content),
            @ApiResponse(responseCode = "404", description = "Teacher not found", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<TeacherDto> updateTeacher(@Parameter(description = "Id of the teacher") @PathVariable int id,
                                                    @RequestBody TeacherDto teacherDto) {
        TeacherDto dto = service.updateTeacher(id, teacherDto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Operation(summary = "Delete teacher by id", description = "Deletes a teacher by id. If there is no teacher with such id, returns 404.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Teacher deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "Teacher not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeacherById(@Parameter(description = "Id of the teacher") @PathVariable int id) {
        service.deleteTeacher(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Add subject to teacher", description = "Adds a subject to a teacher. If there is no teacher with such id, returns 404.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Subject added", content = @Content),
            @ApiResponse(responseCode = "409", description = "Subject already added", content = @Content),
            @ApiResponse(responseCode = "404", description = "Teacher not found", content = @Content)
    })
    @PutMapping("/{id}/subjects")
    public ResponseEntity<Void> addSubjectToTeacher(@Parameter(description = "Id of the teacher") @PathVariable int id,
                                                    @Valid @RequestBody SubjectDto dto) {
        service.addSubjectToTeacher(id, dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Get all subjects of teacher",
            description = "Returns a list of subjects of a teacher. If there is no teacher with such id, returns 404.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of subjects",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = SubjectDto.class)))),
            @ApiResponse(responseCode = "404", description = "Teacher not found", content = @Content)
    })
    @GetMapping("/{id}/subjects")
    public ResponseEntity<List<SubjectDto>> getAllSubjectsOfTeacher(@Parameter(description = "Id of the teacher") @PathVariable int id) {
        List<SubjectDto> teachersDto = service.getAllSubjectsOfTeacher(id);
        return new ResponseEntity<>(teachersDto, HttpStatus.OK);
    }

    @Operation(summary = "Delete subject from teacher",
            description = "Deletes a subject from a teacher. If there is no teacher with such id, returns 404.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Subject deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "Teacher or subject not found", content = @Content)
    })
    @DeleteMapping("/{id}/subjects")
    public ResponseEntity<Void> deleteSubjectOfTeacher(@Parameter(description = "Id of the teacher") @PathVariable int id,
                                                       @Parameter(description = "Id of the subject") @RequestParam int subjectId) {
        service.deleteSubjectOfTeacher(id, subjectId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Get all students of teacher",
            description = "Returns a list of students of a teacher. If there is no teacher with such id, returns 404.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of students",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = StudentDto.class)))),
            @ApiResponse(responseCode = "404", description = "Teacher not found", content = @Content)
    })
    @GetMapping("/{id}/students")
    public ResponseEntity<List<StudentDto>> getAllStudentsOfTeacher(@Parameter(description = "Id of the teacher") @PathVariable int id) {
        List<StudentDto> studentsDto = service.getAllStudentsOfTeacher(id);
        return new ResponseEntity<>(studentsDto, HttpStatus.OK);
    }

    @Operation(summary = "Add student to teacher", description = "Adds a student to a teacher. If there is no teacher with such id, returns 404.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Student added", content = @Content),
            @ApiResponse(responseCode = "409", description = "Student already added", content = @Content),
            @ApiResponse(responseCode = "404", description = "Teacher or student not found", content = @Content)
    })
    @PutMapping("/{id}/students")
    public ResponseEntity<Void> addStudentToTeacher(@Parameter(description = "Id of the teacher") @PathVariable int id,
                                                    @Parameter(description = "Id of the student") @RequestParam int studentId) {
        service.addStudentToTeacher(id, studentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Delete student from teacher",
            description = "Deletes a student from a teacher. If there is no teacher with such id, returns 404.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Student deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "Teacher or student not found", content = @Content)
    })
    @DeleteMapping("/{id}/students")
    public ResponseEntity<Void> deleteStudentOfTeacher(@Parameter(description = "Id of the teacher") @PathVariable int id,
                                                       @Parameter(description = "Id of the student") @RequestParam int studentId) {
        service.deleteStudentOfTeacher(id, studentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

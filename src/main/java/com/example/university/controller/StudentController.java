package com.example.university.controller;

import com.example.university.dto.StudentDto;
import com.example.university.dto.SubjectDto;
import com.example.university.dto.TeacherDto;
import com.example.university.service.StudentService;
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
 * REST controller for managing {@link com.example.university.entity.Student} entities.
 *
 * <p>This controller handles HTTP requests related to student operations, such as creating,
 * reading, updating, and deleting student data. It is mapped to the {@code /api/students} endpoint.
 *
 * <p>The {@code @RestController} annotation indicates that this class is a REST controller.
 * The {@code @Tag} annotation is used for OpenAPI documentation, tagging this controller
 * with the name "Student".
 */
@Tag(name = "Student")
@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    StudentService service;

    @Operation(summary = "Get all students", description = "Returns a list of students")
    @ApiResponse(responseCode = "200", description = "List of students",
            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = StudentDto.class))))
    @GetMapping
    public ResponseEntity<List<StudentDto>> getAllStudents() {
        List<StudentDto> list = service.getAllStudents();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @Operation(summary = "Get student by id", description = "Returns a student by id. If there is no student with such id, returns 404.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Student found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = StudentDto.class))),
            @ApiResponse(responseCode = "404", description = "Student not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> getStudentById(@Parameter(description = "Id of the student") @PathVariable int id) {
        StudentDto studentDto = service.getStudentById(id);
        return new ResponseEntity<>(studentDto, HttpStatus.OK);
    }

    @Operation(summary = "Create new student", description = "Creates a new student")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Student created",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = StudentDto.class))),
            @ApiResponse(responseCode = "400", description = "Validation failed", content = @Content)
    })
    @PostMapping
    public ResponseEntity<StudentDto> saveStudent(@Valid @RequestBody StudentDto studentDto) {
        StudentDto dto = service.saveStudent(studentDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @Operation(summary = "Update student by id", description = "Updates a student by id. If there is no student with such id, returns 404.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Student updated",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = StudentDto.class))),
            @ApiResponse(responseCode = "400", description = "Validation failed", content = @Content),
            @ApiResponse(responseCode = "404", description = "Student not found", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<StudentDto> updateStudent(@Parameter(description = "Id of the student") @PathVariable int id,
                                                    @RequestBody StudentDto studentDto) {
        StudentDto dto = service.updateStudent(id, studentDto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Operation(summary = "Delete student by id", description = "Deletes a student by id. If there is no student with such id, returns 404.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Student deleted"),
            @ApiResponse(responseCode = "404", description = "Student not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudentById(@Parameter(description = "Id of the student") @PathVariable int id) {
        service.deleteStudent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Add teacher to student", description = "Adds a teacher to a student")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Teacher added",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TeacherDto.class))),
            @ApiResponse(responseCode = "409", description = "Teacher already added", content = @Content),
            @ApiResponse(responseCode = "404", description = "Student or teacher not found", content = @Content)
    })
    @PutMapping("/{id}/teachers")
    public ResponseEntity<Void> addTeacherToStudent(@Parameter(description = "Id of the student") @PathVariable int id,
                                                    @Parameter(description = "Id of the teacher") @RequestParam int setTeacher) {
        service.addTeacherToStudent(id, setTeacher);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Get all teachers of student", description = "Returns a list of teachers of a student")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of teachers",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = TeacherDto.class)))),
            @ApiResponse(responseCode = "404", description = "Student not found", content = @Content)
    })
    @GetMapping("/{id}/teachers")
    public ResponseEntity<List<TeacherDto>> getAllTeachersOfStudent(@Parameter(description = "Id of the student") @PathVariable int id) {
        List<TeacherDto> teachersDto = service.getAllTeachersOfStudent(id);
        return new ResponseEntity<>(teachersDto, HttpStatus.OK);
    }

    @Operation(summary = "Delete teacher from student", description = "Deletes a teacher from a student")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Teacher deleted"),
            @ApiResponse(responseCode = "404", description = "Student or teacher not found", content = @Content)
    })
    @DeleteMapping("/{id}/teachers")
    public ResponseEntity<Void> deleteTeacherFromStudent(@Parameter(description = "Id of the student") @PathVariable int id,
                                                         @Parameter(description = "Id of the teacher") @RequestParam int setTeacher) {
        service.deleteTeacherFromStudent(id, setTeacher);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Add subject to student", description = "Adds a subject to a student")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Subject added",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = SubjectDto.class))),
            @ApiResponse(responseCode = "409", description = "Subject already added", content = @Content),
            @ApiResponse(responseCode = "404", description = "Student or subject not found", content = @Content)
    })
    @PutMapping("/{id}/subjects")
    public ResponseEntity<Void> addSubjectToStudent(@Parameter(description = "Id of the student") @PathVariable int id,
                                                    @Parameter(description = "Id of the subject") @RequestParam int setSubject) {
        service.addSubjectToStudent(id, setSubject);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Get all subjects of student", description = "Returns a list of subjects of a student")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of subjects",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = SubjectDto.class)))),
            @ApiResponse(responseCode = "404", description = "Student not found", content = @Content)
    })
    @GetMapping("/{id}/subjects")
    public ResponseEntity<List<SubjectDto>> getAllSubjectsOfStudent(@Parameter(description = "Id of the student") @PathVariable int id) {
        List<SubjectDto> subjectsDto = service.getAllSubjectsOfStudent(id);
        return new ResponseEntity<>(subjectsDto, HttpStatus.OK);
    }

    @Operation(summary = "Delete subject from student", description = "Deletes a subject from a student")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Subject deleted"),
            @ApiResponse(responseCode = "404", description = "Student or subject not found", content = @Content)
    })
    @DeleteMapping("/{id}/subjects")
    public ResponseEntity<Void> deleteSubjectFromStudent(@Parameter(description = "Id of the student") @PathVariable int id,
                                                         @Parameter(description = "Id of the subject") @RequestParam int setSubject) {
        service.deleteSubjectFromStudent(id, setSubject);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

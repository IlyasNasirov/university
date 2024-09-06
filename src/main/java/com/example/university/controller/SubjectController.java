package com.example.university.controller;

import com.example.university.dto.SubjectDto;
import com.example.university.service.SubjectService;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Subject")
@RestController
@RequestMapping("/api/subjects")
public class SubjectController {

    @Autowired
    SubjectService service;

    @Operation(summary = "Get all subjects", description = "Returns a list of subjects")
    @ApiResponse(responseCode = "200", description = "List of subjects", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = SubjectDto.class))))
    @GetMapping
    public ResponseEntity<List<SubjectDto>> getAllSubject() {
        List<SubjectDto> list = service.getAllSubjects();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @Operation(summary = "Get subject by id", description = "Returns a subject by id. If there is no subject with such id, returns 404.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Subject found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SubjectDto.class))),
            @ApiResponse(responseCode = "404", description = "Subject not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<SubjectDto> getSubjectById(@Parameter(description = "Id of the subject") @PathVariable int id) {
        SubjectDto subjectDto = service.getSubjectById(id);
        return new ResponseEntity<>(subjectDto, HttpStatus.OK);
    }

    @Operation(summary = "Create new subject", description = "Creates a new subject")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Subject created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SubjectDto.class))),
            @ApiResponse(responseCode = "400", description = "Validation failed", content = @Content)
    })
    @PostMapping
    public ResponseEntity<?> saveSubject(@Valid @RequestBody SubjectDto subjectDto) {
        SubjectDto dto = service.saveSubject(subjectDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @Operation(summary = "Delete subject by id", description = "Deletes a subject by id. If there is no subject with such id, returns 404.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Subject deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "Subject not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubjectById(@Parameter(description = "Id of the subject") @PathVariable int id) {
        service.deleteSubject(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Update subject by id", description = "Updates a subject by id. If there is no subject with such id, returns 404.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Subject updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SubjectDto.class))),
            @ApiResponse(responseCode = "400", description = "Validation failed", content = @Content),
            @ApiResponse(responseCode = "404", description = "Subject not found", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<SubjectDto> updateSubject(@Parameter(description = "Id of the subject") @PathVariable int id, @RequestBody SubjectDto subjectDto) {
        SubjectDto dto = service.updateSubject(id, subjectDto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

}

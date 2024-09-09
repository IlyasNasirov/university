package com.example.university.service;

import com.example.university.dto.StudentDto;
import com.example.university.dto.SubjectDto;
import com.example.university.dto.TeacherDto;
import com.example.university.entity.Student;
import com.example.university.entity.Subject;
import com.example.university.entity.Teacher;
import com.example.university.exception.EntityAlreadyAddedException;
import com.example.university.exception.NoEntityFoundException;
import com.example.university.mapper.StudentMapper;
import com.example.university.mapper.SubjectMapper;
import com.example.university.mapper.TeacherMapper;
import com.example.university.repository.StudentRepository;
import com.example.university.repository.SubjectRepository;
import com.example.university.repository.TeacherRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the {@link TeacherService} interface.
 *
 * <p>This class provides the business logic for managing teachers in the application.
 * It is annotated with {@code @Service} to mark it as a Spring service component.
 * The {@code @AllArgsConstructor} annotation from Lombok generates a constructor with all required fields.
 */
@Service
@AllArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private TeacherRepository teacherRepo;

    private SubjectRepository subjectRepo;

    private StudentRepository studentRepo;

    private TeacherMapper teacherMapper;

    private SubjectMapper subjectMapper;

    private StudentMapper studentMapper;

    /**
     * Gets a list of all teachers.
     *
     * @return a list of teacher DTOs
     */
    @Override
    public List<TeacherDto> getAllTeachers() {
        List<Teacher> list = teacherRepo.findAll();
        return list.stream()
                .map(teacherMapper::entityToDto)
                .collect(Collectors.toList());
    }

    /**
     * Gets a teacher by id.
     *
     * @param id of the teacher
     * @return a teacher DTO
     * @throws NoEntityFoundException if the teacher with such id doesn't exist
     */
    @Override
    public TeacherDto getTeacherById(int id) {
        Teacher teacher = teacherRepo.findById(id)
                .orElseThrow(() -> new NoEntityFoundException("No teacher found with id " + id));
        return teacherMapper.entityToDto(teacher);
    }

    /**
     * Saves a teacher.
     *
     * @param teacherDto a teacher DTO
     * @return the saved teacher DTO
     * @throws MethodArgumentNotValidException if there are validation errors
     */
    @Override
    public TeacherDto saveTeacher(TeacherDto teacherDto) {
        Teacher teacher = teacherMapper.dtoToEntity(teacherDto);
        teacherRepo.save(teacher);
        return teacherMapper.entityToDto(teacher);
    }

    /**
     * Updates a teacher.
     *
     * @param teacherId of the teacher
     * @param dto       a teacher DTO
     * @return the updated teacher DTO
     * @throws NoEntityFoundException if the teacher with such id doesn't exist
     */
    @Override
    public TeacherDto updateTeacher(int teacherId, TeacherDto dto) {
        Teacher teacher = teacherRepo.findById(teacherId)
                .orElseThrow(() -> new NoEntityFoundException("Teacher is not found with id " + teacherId));
        teacher.setFirstName(dto.getFirstName());
        teacher.setLastName(dto.getLastName());
        teacher.setMiddleName(dto.getMiddleName());
        teacher.setAge(dto.getAge());
        teacherRepo.save(teacher);
        return teacherMapper.entityToDto(teacher);
    }

    /**
     * Deletes a teacher.
     *
     * @param id of the teacher
     * @throws NoEntityFoundException if the teacher with such id doesn't exist
     */
    @Override
    public void deleteTeacher(int id) {
        teacherRepo.findById(id).orElseThrow(() -> new NoEntityFoundException("There is no teacher with id " + id));
        teacherRepo.deleteById(id);
    }

    /**
     * Adds a subject to the teacher.
     *
     * @param teacherId  of the teacher
     * @param subjectDto a subject DTO
     * @throws EntityAlreadyAddedException     if the teacher already teaches the subject
     * @throws MethodArgumentNotValidException if there are validation errors
     */
    @Override
    public void addSubjectToTeacher(int teacherId, SubjectDto subjectDto) {
        Teacher teacher = teacherRepo.findById(teacherId)
                .orElseThrow(() -> new NoEntityFoundException("Teacher is not found with id " + teacherId));
        Subject subject = subjectMapper.dtoToEntity(subjectDto);
        if (teacher.getSubjects().stream().anyMatch(sub -> sub.getName().equalsIgnoreCase(subject.getName()))) {
            throw new EntityAlreadyAddedException("Teacher already teaches the subject with name " + subject.getName());
        }
        teacher.getSubjects().add(subject);
        subject.setTeacher(teacher);
        teacherRepo.save(teacher);
    }

    /**
     * Gets a list of all subjects of the teacher.
     *
     * @param id of the teacher
     * @return a list of subject DTOs
     * @throws NoEntityFoundException if the teacher with such id doesn't exist
     */
    @Override
    public List<SubjectDto> getAllSubjectsOfTeacher(int id) {
        Teacher teacher = teacherRepo.findById(id)
                .orElseThrow(() -> new NoEntityFoundException("There is no teacher with id " + id));
        List<Subject> subjects = teacher.getSubjects();
        return subjects.stream().map(subjectMapper::entityToDto).collect(Collectors.toList());
    }

    /**
     * Deletes a subject from the teacher.
     *
     * @param teacherId of the teacher
     * @param subjectId of the subject
     * @throws NoEntityFoundException if the teacher or subject with such id doesn't exist
     */
    @Override
    public void deleteSubjectOfTeacher(int teacherId, int subjectId) {
        Teacher teacher = teacherRepo.findById(teacherId)
                .orElseThrow(() -> new NoEntityFoundException("Teacher is not found with id " + teacherId));
        Subject subject = subjectRepo.findById(subjectId)
                .orElseThrow(() -> new NoEntityFoundException("Subject is not found with id " + subjectId));
        teacher.getSubjects().remove(subject);
        teacherRepo.save(teacher);
    }

    /**
     * Gets a list of all students of the teacher.
     *
     * @param teacherId of the teacher
     * @return a list of student DTOs
     * @throws NoEntityFoundException if the teacher with such id doesn't exist
     */
    @Override
    public List<StudentDto> getAllStudentsOfTeacher(int teacherId) {
        Teacher teacher = teacherRepo.findById(teacherId)
                .orElseThrow(() -> new NoEntityFoundException("Teacher is not found with id " + teacherId));
        List<Student> students = teacher.getStudents();
        return students.stream().map(studentMapper::entityToDto).collect(Collectors.toList());
    }

    /**
     * Deletes a student from the teacher.
     *
     * @param teacherId of the teacher
     * @param studentId of the student
     * @throws NoEntityFoundException if the teacher or student with such id doesn't exist
     */
    @Override
    public void deleteStudentOfTeacher(int teacherId, int studentId) {
        Teacher teacher = teacherRepo.findById(teacherId)
                .orElseThrow(() -> new NoEntityFoundException("Teacher is not found with id " + teacherId));
        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new NoEntityFoundException("Student is not found with id " + studentId));
        teacher.getStudents().remove(student);
        teacherRepo.save(teacher);
    }

    /**
     * Adds a student to the teacher.
     *
     * @param teacherId of the teacher
     * @param studentId of the student
     * @throws NoEntityFoundException if the teacher or student with such id doesn't exist
     */
    @Override
    public void addStudentToTeacher(int teacherId, int studentId) {
        Teacher teacher = teacherRepo.findById(teacherId)
                .orElseThrow(() -> new NoEntityFoundException("Teacher is not found with id " + teacherId));
        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new NoEntityFoundException("Student is not found with id " + studentId));
        teacher.getStudents().add(student);
        teacherRepo.save(teacher);
    }

}

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
import java.util.List;
import java.util.stream.Collectors;


/**
 * Implementation of the {@link StudentService} interface.
 *
 * <p>This class provides the business logic for managing students in the application.
 * It is annotated with {@code @Service} to indicate that it's a service component in the Spring context.
 * The {@code @AllArgsConstructor} annotation from Lombok is used to generate a constructor with all fields.
 */
@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepo;

    private TeacherRepository teacherRepo;

    private SubjectRepository subjectRepo;

    private StudentMapper studentMapper;

    private TeacherMapper teacherMapper;

    private SubjectMapper subjectMapper;

    /**
     * Gets student by id
     *
     * @param id of student
     * @return studentDto
     * @throws NoEntityFoundException if student with such id doesn't exist
     */
    @Override
    public StudentDto getStudentById(int id) {
        Student student = studentRepo.findById(id)
                .orElseThrow(() -> new NoEntityFoundException("There is no student with id " + id));
        return studentMapper.entityToDto(student);
    }
    /**
     * Gets all students
     *
     * @return list of studentDtos
     */

    @Override
    public List<StudentDto> getAllStudents() {
        List<Student> list = studentRepo.findAll();
        return list.stream()
                .map(studentMapper::entityToDto)
                .collect(Collectors.toList());
    }
    /**
     * Saves student
     *
     * @param studentDto student to be saved
     *
     * @return saved student
     * @throws NoEntityFoundException if student with such id doesn't exist
     * @throws EntityAlreadyAddedException if student with such id and first name already exists
     */

    @Override
    public StudentDto saveStudent(StudentDto studentDto) {
        Student student = studentMapper.dtoToEntity(studentDto);
        studentRepo.save(student);
        return studentMapper.entityToDto(student);
    }

    /**
     * Updates student
     *
     * @param id of student to be updated
     * @param studentDto student to be updated
     * @return updated student
     * @throws NoEntityFoundException if student with such id doesn't exist
     */

    @Override
    public StudentDto updateStudent(int id, StudentDto studentDto) {
        Student student = studentRepo.findById(id)
                .orElseThrow(() -> new NoEntityFoundException("There is no student with id " + id));
        student.setFirstName(studentDto.getFirstName());
        student.setLastName(studentDto.getLastName());
        student.setMiddleName(studentDto.getMiddleName());
        student.setAge(studentDto.getAge());
        studentRepo.save(student);
        return studentMapper.entityToDto(student);
    }

    /**
     * Deletes student
     *
     *  @param id of student to be deleted
     * @throws NoEntityFoundException if student with such id doesn't exist
     */

    @Override
    public void deleteStudent(int id) {
        studentRepo.findById(id).orElseThrow(() -> new NoEntityFoundException("There is no student with id " + id));
        studentRepo.deleteById(id);
    }

    /**
     * Adds teacher to student
     *
     * @param studentId id of student
     * @param teacherId id of teacher to be added
     * @throws NoEntityFoundException if student or teacher with such id doesn't exist
     * @throws EntityAlreadyAddedException if teacher with such id already exists
     */

    @Override
    public void addTeacherToStudent(int studentId, int teacherId) {
        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new NoEntityFoundException("There is no student with id " + studentId));
        Teacher teacher = teacherRepo.findById(teacherId)
                .orElseThrow(() -> new NoEntityFoundException("There is no teacher with id " + teacherId));
        if (student.getTeachers().contains(teacher)) {
            throw new EntityAlreadyAddedException("There is already a teacher with id " + teacherId);
        }
        teacher.getStudents().add(student);
        teacherRepo.save(teacher);
    }

    /**
     * Gets all teachers of student
     *
     * @param studentId id of student
     *
     * @return list of teachersDtos
     * @throws NoEntityFoundException if student with such id doesn't exist
     */

    @Override
    public List<TeacherDto> getAllTeachersOfStudent(int studentId) {
        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new NoEntityFoundException("There is no student with id " + studentId));
        List<Teacher> teachers = student.getTeachers();
        return teachers.stream().map(teacherMapper::entityToDto).collect(Collectors.toList());
    }

    /**
     * Gets all subjects of student
     *
     * @param studentId id of student
     * @return list of subjectsDtos
     * @throws NoEntityFoundException if student with such id doesn't exist
     */

    @Override
    public List<SubjectDto> getAllSubjectsOfStudent(int studentId) {
        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new NoEntityFoundException("There is no student with id " + studentId));
        List<Subject> subjects = student.getSubjects();
        return subjects.stream().map(subjectMapper::entityToDto).collect(Collectors.toList());
    }

    /**
     * Adds subject to student
     *
     * @param studentId id of student
     *@param subjectId id of subject to be added
     * @throws NoEntityFoundException if student or teacher with such id doesn't exist
     * @throws EntityAlreadyAddedException if subject with such id already exists
     */

    @Override
    public void addSubjectToStudent(int studentId, int subjectId) {
        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new NoEntityFoundException("There is no student with id " + studentId));
        Subject subject = subjectRepo.findById(subjectId)
                .orElseThrow(() -> new NoEntityFoundException("There is no subject with id " + subjectId));
        if (student.getSubjects().contains(subject)) {
            throw new EntityAlreadyAddedException("There is already a subject with id " + subjectId);
        }
        student.getSubjects().add(subject);
        studentRepo.save(student);
    }

    /**
     * Deletes subject from student
     *
     * @param studentId id of student
     * @param subjectId id of subject to be deleted
     * @throws NoEntityFoundException if student or subject with such id doesn't exist
     */

    @Override
    public void deleteSubjectFromStudent(int studentId, int subjectId) {
        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new NoEntityFoundException("There is no student with id " + studentId));
        Subject subject = subjectRepo.findById(subjectId)
                .orElseThrow(() -> new NoEntityFoundException("There is no subject with id " + subjectId));
        student.getSubjects().remove(subject);
        studentRepo.save(student);
    }

    /**
     * Deletes teacher from student
     *
     * @param studentId id of student
     * @param teacherId id of teacher to be deleted
     * @throws NoEntityFoundException if student or teacher with such id doesn't exist
     */

    @Override
    public void deleteTeacherFromStudent(int studentId, int teacherId) {
        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new NoEntityFoundException("There is no student with id " + studentId));
        Teacher teacher = teacherRepo.findById(teacherId)
                .orElseThrow(() -> new NoEntityFoundException("There is no teacher with id " + teacherId));
        teacher.getStudents().remove(student);
        teacherRepo.save(teacher);
    }

}

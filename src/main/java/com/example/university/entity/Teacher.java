package com.example.university.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * Represents a teacher in the system.
 *
 * <p>This class includes basic teacher information and their associations with students
 * and subjects.
 *
 * <p>The {@code id} is a unique identifier generated automatically. The {@code students}
 * field represents a many-to-many relationship with {@link Student}, and the
 * {@code subjects} field represents a many-to-many relationship with {@link Subject}.
 *
 * <p>{@code @JsonManagedReference} and {@code @JsonBackReference} annotations are used
 * to manage JSON serialization and prevent cyclic references.
 *
 * @see Student
 * @see Subject
 */

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "teachers")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String firstName;

    private String lastName;

    private String middleName;

    private int age;

    /**
     * List of subjects associated with this teacher.
     *
     * <p>This is the owning side of the many-to-many relationship between the Teacher
     * and Subject entities. The {@code @JoinTable} annotation specifies the join table
     * "teacher_subject" that links the "teacher_id" and "subject_id" columns.
     *
     * <p>The {@code @JsonManagedReference} annotation is used to handle the serialization
     * of this field in JSON. It marks this side of the relationship as the managed side,
     * allowing the "subjects" field to be serialized while avoiding cyclic references.
     */
    @OneToMany(mappedBy = "teacher",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH}, orphanRemoval = true)
    @JsonManagedReference
    private List<Subject> subjects;

    /**
     * List of students associated with this teacher.
     *
     * <p>This is the owning side of the many-to-many relationship between the Teacher
     * and Student entities. The {@code @JoinTable} annotation specifies the join table
     * "teacher_student" that links the "teacher_id" and "student_id" columns.
     *
     * <p>The {@code @JsonManagedReference} annotation is used to handle the serialization
     * of this field in JSON. It marks this side of the relationship as the managed side,
     * allowing the "students" field to be serialized while avoiding cyclic references.
     *
     * @see Student#getTeachers()
     */
    @ManyToMany
    @JoinTable(name = "teacher_student", joinColumns = @JoinColumn(name = "teacher_id"), inverseJoinColumns = @JoinColumn(name = "student_id"))
    @JsonManagedReference
    private List<Student> students;


}

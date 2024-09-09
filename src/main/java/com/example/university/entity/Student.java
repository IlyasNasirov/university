package com.example.university.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;
/**
 * Represents a student in the system.
 *
 * <p>This class includes basic student information and their associations with teachers
 * and subjects.
 *
 * <p>The {@code id} is a unique identifier generated automatically. The {@code teachers}
 * field represents a many-to-many relationship with {@link Teacher}, and the
 * {@code subjects} field represents a many-to-many relationship with {@link Subject}.
 *
 * <p>{@code @JsonBackReference} and {@code @JsonManagedReference} annotations are used
 * to manage JSON serialization and prevent cyclic references.
 *
 * @see Teacher
 * @see Subject
 */

@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String firstName;

    private String lastName;

    private String middleName;

    private int age;
    /**
     * List of teachers associated with this student.
     *
     * <p>This is a many-to-many relationship between the Student and Teacher entities.
     * The {@code @ManyToMany} annotation indicates that this is the inverse side of
     * the relationship, and it is mapped by the "students" field in the {@link Teacher} entity.
     *
     * <p>The {@code @JsonBackReference} annotation is used to prevent cyclic references
     * during JSON serialization. The "teachers" field will be omitted from the JSON
     * output to avoid recursive links.
     *
     * @see Teacher#getStudents()
     */

    @ManyToMany(mappedBy = "students")
    @JsonBackReference
    private List<Teacher> teachers;
    /**
     * List of subjects associated with this student.
     *
     * <p>This is the owning side of the many-to-many relationship between the Student
     * and Subject entities. The {@code @JoinTable} annotation specifies the join table
     * "student_subject", linking the "student_id" column to the "subject_id" column.
     *
     * <p>The {@code @JsonManagedReference} annotation is used to handle the serialization
     * of this field in JSON. It marks this side of the relationship as the managed side,
     * allowing the "subjects" field to be serialized while avoiding cyclic references.
     *
     * @see Subject#getStudents()
     */

    @ManyToMany
    @JoinTable(name = "student_subject", joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "subject_id"))
    @JsonManagedReference
    private List<Subject> subjects;

}

package com.example.university.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.List;

/**
 * Represents a subject in the system.
 *
 * <p>This class includes basic subject information and their associations with teachers
 * and students.
 *
 * <p>The {@code id} is a unique identifier generated automatically. The {@code teacher}
 * field represents a many-to-one relationship with {@link Teacher}, and the
 * {@code students} field represents a many-to-many relationship with {@link Student}.
 *
 * <p>{@code @JsonBackReference} and {@code @JsonManagedReference} annotations are used
 * to manage JSON serialization and prevent cyclic references.
 *
 * @see Teacher
 * @see Student
 */
@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "subjects")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    /**
     * The teacher associated with this entity.
     *
     * <p>This field represents a many-to-one relationship with the {@link Teacher} entity.
     * It is used to link a student to a specific teacher.
     *
     * <p>The {@code @JoinColumn} annotation specifies the column "teacher_id" in the database
     * that is used to establish this relationship. The {@code @JsonBackReference} annotation
     * is used to prevent cyclic references during JSON serialization.
     *
     * @see Teacher
     */
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "teacher_id")
    @JsonBackReference
    private Teacher teacher;

    /**
     * The list of students associated with this entity.
     *
     * <p>This field represents a many-to-many relationship with the {@link Student} entity.
     * It is used to link a subject to a specific list of students.
     *
     * <p>The {@code @JsonBackReference} annotation is used to prevent cyclic references
     * during JSON serialization.
     *
     * @see Student
     */
    @ManyToMany(mappedBy = "subjects")
    @JsonBackReference
    private List<Student> students;
}

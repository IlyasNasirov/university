package com.example.university.repository;

import com.example.university.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for {@link Student} entities.
 *
 * <p>This interface extends {@link JpaRepository}, providing CRUD operations and query methods
 * for {@link Student} entities with {@code Integer} as the primary key type.
 */
public interface StudentRepository extends JpaRepository<Student, Integer> {

}

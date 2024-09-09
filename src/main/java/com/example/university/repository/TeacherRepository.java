package com.example.university.repository;

import com.example.university.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for {@link Teacher} entities.
 *
 * <p>This interface extends {@link JpaRepository}, providing CRUD operations and query methods
 * for {@link Teacher} entities with {@code Integer} as the primary key type.
 */
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {

}

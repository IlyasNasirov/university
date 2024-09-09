package com.example.university.repository;

import com.example.university.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for {@link Subject} entities.
 *
 * <p>This interface extends {@link JpaRepository}, providing CRUD operations and query methods
 * for {@link Subject} entities with {@code Integer} as the primary key type.
 */
public interface SubjectRepository extends JpaRepository<Subject, Integer> {

}

package com.example.university.repository;

import com.example.university.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher,Integer> {

    @Query("SELECT t FROM Teacher t WHERE LOWER(t.firstName) = LOWER(:name) OR LOWER(t.lastName) = LOWER(:name)")
    Optional<Teacher> findByFirstNameOrLastNameIgnoreCase(@Param("name") String name);
}

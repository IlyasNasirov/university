package com.example.university.repository;

import com.example.university.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject,Integer> {

    Optional<Subject> findByNameIgnoreCase(String name);

}

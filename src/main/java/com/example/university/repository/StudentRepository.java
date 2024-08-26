package com.example.university.repository;

import com.example.university.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Id;

public interface StudentRepository extends JpaRepository<Student, Integer> {

}

package com.example.university.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@Table(name = "teachers")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String lastName;
    private String middleName;
    private int age;


    @OneToMany(mappedBy = "teacher", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH}, orphanRemoval = true)
    @JsonManagedReference
    private List<Subject> subjects;

    public void addSubject(Subject subject){
        subjects.add(subject);
        subject.setTeacher(this);
    }
    public void removeSubject(Subject subject){
        subjects.remove(subject);
        subject.setTeacher(null);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher teacher = (Teacher) o;
        return Objects.equals(id, teacher.id);
    }
}

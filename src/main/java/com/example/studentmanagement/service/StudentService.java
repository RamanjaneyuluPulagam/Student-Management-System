package com.example.studentmanagement.service;

import com.example.studentmanagement.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface StudentService {

    Student saveStudent(Student student);

    Student updateStudent(Long id, Student student);

    void deleteStudent(Long id);

    Optional<Student> getStudentById(Long id);

    Page<Student> getAllStudents(Pageable pageable);

    Page<Student> searchByName(String name, Pageable pageable);

    Page<Student> filterByClass(String studentClass, Pageable pageable);
}

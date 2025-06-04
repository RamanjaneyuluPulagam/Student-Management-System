package com.example.studentmanagement.service.impl;

import com.example.studentmanagement.model.Student;
import com.example.studentmanagement.repository.StudentRepository;
import com.example.studentmanagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository repository;

    @Override
    public Student saveStudent(Student student) {
        return repository.save(student);
    }

    @Override
    public Student updateStudent(Long id, Student student) {
        Optional<Student> existing = repository.findById(id);
        if (existing.isEmpty()) throw new RuntimeException("Student not found");

        Student s = existing.get();
        s.setName(student.getName());
        s.setAge(student.getAge());
        s.setStudentClass(student.getStudentClass());
        s.setEmail(student.getEmail());
        s.setAddress(student.getAddress());

        return repository.save(s);
    }

    @Override
    public void deleteStudent(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Student> getStudentById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Page<Student> getAllStudents(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Page<Student> searchByName(String name, Pageable pageable) {
        return repository.findByNameContainingIgnoreCase(name, pageable);
    }

    @Override
    public Page<Student> filterByClass(String studentClass, Pageable pageable) {
        return repository.findByStudentClass(studentClass, pageable);
    }
}

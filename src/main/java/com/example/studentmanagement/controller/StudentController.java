package com.example.studentmanagement.controller;

import com.example.studentmanagement.model.Student;
import com.example.studentmanagement.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public String listStudents(Model model,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(required = false) String searchName,
                               @RequestParam(required = false) String filterClass) {
        Page<Student> students;

        if (searchName != null && !searchName.isBlank()) {
            students = studentService.searchByName(searchName, PageRequest.of(page, 5));
        } else if (filterClass != null && !filterClass.isBlank()) {
            students = studentService.filterByClass(filterClass, PageRequest.of(page, 5));
        } else {
            students = studentService.getAllStudents(PageRequest.of(page, 5));
        }

        model.addAttribute("students", students.getContent());
        model.addAttribute("totalPages", students.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("searchName", searchName);
        model.addAttribute("filterClass", filterClass);

        return "students/list";
    }

    @GetMapping("/add")
    public String showAddForm(Student student) {
        return "students/add-student";
    }

    @PostMapping("/add")
    public String addStudent(@Valid Student student, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "students/add-student";
        }
        studentService.saveStudent(student);
        return "redirect:/students";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Student student = studentService.getStudentById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student Id:" + id));
        model.addAttribute("student", student);
        return "students/update-student";
    }

    @PostMapping("/update/{id}")
    public String updateStudent(@PathVariable("id") long id, @Valid Student student,
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            student.setStudentId(id);
            return "students/update-student";
        }
        studentService.updateStudent(id, student);
        return "redirect:/students";
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable("id") long id) {
        studentService.deleteStudent(id);
        return "redirect:/students";
    }
}

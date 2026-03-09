package com.student_management.student_management.controller;

import com.student_management.student_management.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.JsonNode;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/create")
    public JsonNode createStudent(@RequestBody JsonNode jsonNode){
        return studentService.createStudent(jsonNode);
    }

    @GetMapping("/getAll")
    public JsonNode getAllStudent(){
        return studentService.getAllStudent();
    }

    @GetMapping("/{studentId}")
    public JsonNode getStudentById(@PathVariable("studentId") Integer studentId){
        return studentService.getStudentById(studentId);
    }

    @DeleteMapping("/{studentId}")
    public JsonNode deleteStudentById(@PathVariable("studentId") Integer studentId){
        return studentService.deleteStudentById(studentId);
    }
}

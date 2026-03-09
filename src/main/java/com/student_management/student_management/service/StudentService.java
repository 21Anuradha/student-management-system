package com.student_management.student_management.service;

import com.student_management.student_management.model.Student;
import com.student_management.student_management.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ObjectNode;

import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;
    final ObjectMapper objectMapper = new ObjectMapper();


    public JsonNode createStudent(JsonNode jsonNode) {
        ObjectNode response = objectMapper.createObjectNode();

        Student student;
        if(jsonNode.has("studentId")){
            Integer studentId = jsonNode.get("studentId").asInt();

            Optional<Student> optionalStudent = studentRepository.findById(studentId);
            if (optionalStudent.isPresent()){
                student = optionalStudent.get();
            }else {
                response.put("status", "error");
                response.put("message","Student doesnot exist.");
                return response;
            }
        }else {
            student = new Student();
        }
        student.setStudentName(jsonNode.has("studentName")? jsonNode.get("studentName").asText(): null);
        student.setEmailId(jsonNode.has("emailId")? jsonNode.get("emailId").asText(): null);
        student.setPhoneNo(jsonNode.has("phoneNo")? jsonNode.get("phoneNo").asText(): null);

        studentRepository.save(student);

        response.put("status","Success");
        response.put("message","Data Saved Successfully");
        response.put("studentId",student.getStudentId());

        return response;
    }
}

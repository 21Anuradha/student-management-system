package com.student_management.student_management.service;

import com.student_management.student_management.model.Student;
import com.student_management.student_management.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ArrayNode;
import tools.jackson.databind.node.ObjectNode;

import java.util.List;
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

    public JsonNode getAllStudent() {
        ObjectNode response = objectMapper.createObjectNode();

        List<Student> studentList = studentRepository.findAll();
        if (studentList.isEmpty()){
            response.put("status","error");
            response.put("message","No Student found.");
            return response;
        }
        ArrayNode arrayNode = objectMapper.createArrayNode();

        for (Student student: studentList){
            ObjectNode studentNode = objectMapper.createObjectNode();
            studentNode.put("studentId", student.getStudentId());
            studentNode.put("studentName", student.getStudentName());
            studentNode.put("emailId", student.getEmailId());
            studentNode.put("phoneNo", student.getPhoneNo());
            arrayNode.add(studentNode);
        }
        response.put("status", "Success");
        response.put("message", "Student data fetched successfully");
        response.set("studentData", arrayNode);

        return  response;
    }

    public JsonNode getStudentById(Integer studentId) {
        ObjectNode response = objectMapper.createObjectNode();

        Optional<Student> optionalStudent = studentRepository.findById(studentId);

        if (optionalStudent.isPresent()){
            Student student = optionalStudent.get();
            response.put("studentId",student.getStudentId());
            response.put("studentName",student.getStudentName());
            response.put("emailId",student.getEmailId());
            response.put("phoneNo",student.getPhoneNo());
        }else {
            response.put("status","error");
            response.put("message","student does not exist");
        }
        return  response;
    }

    public JsonNode deleteStudentById(Integer studentId) {
        ObjectNode response = objectMapper.createObjectNode();
        Optional<Student> optionalStudent = studentRepository.findById(studentId);

        if (optionalStudent.isPresent()){
            Student student = optionalStudent.get();
            studentRepository.delete(student);
            response.put("status","success");
            response.put("message","Student data deleted successfully");
        }else {
            response.put("status","error");
            response.put("message","student does not exist");
        }
        return  response;
    }
}

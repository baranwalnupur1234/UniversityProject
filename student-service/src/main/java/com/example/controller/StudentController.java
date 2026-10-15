package com.example.controller;


import com.example.entity.Student;
import com.example.request.CreateStudentRequest;
import com.example.response.StudentDTO;
import com.example.response.StudentResponse;
import com.example.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    StudentService studentService;

    @PostMapping("/create")
    public StudentResponse createStudent (@RequestBody CreateStudentRequest createStudentRequest) {
        return studentService.cretaeStudent(createStudentRequest);
    }

    @GetMapping("getById/{id}")
    public StudentResponse getById (@PathVariable long id) {
        return studentService.getById(id);
    }

    @GetMapping("/getByUserName/{userName}")
    public StudentResponse getByUserName(@PathVariable String userName) {
        return studentService.getByUserName(userName);
    }
    @GetMapping("/getNameById/{userName}")
    public StudentDTO getNameById(@PathVariable String userName) {
        return studentService.getNameByUserName(userName);
    }

}

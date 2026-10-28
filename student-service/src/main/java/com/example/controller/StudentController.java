package com.example.controller;


import com.example.request.CreateStudentRequest;
import com.example.response.StudentPageResponse;
import com.example.response.StudentResponse;
import com.example.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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

    @GetMapping("/all")
    public List<StudentResponse> getAllStudent(){
        return studentService.getAllStudentDetails();
    }

    // Add pagination
    @GetMapping("/pagination")
    public StudentPageResponse getAllStudentByPagination(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int size){
        return studentService.getAllStudentByPagination(page, size);
    }

}

package com.example.controller;


import com.example.entity.Student;
import com.example.request.CreateStudentRequest;
import com.example.response.APIResponse;
import com.example.response.StudentDTO;
import com.example.response.StudentResponse;
import com.example.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    StudentService studentService;

    @PostMapping("/create")
    public StudentResponse createStudent (@Valid @RequestBody CreateStudentRequest createStudentRequest) {
        return studentService.createStudent(createStudentRequest);
    }

    @GetMapping("getById/{id}")
    public ResponseEntity<APIResponse<StudentResponse>> getById (@PathVariable long id) {
        StudentResponse studentResponse = studentService.getById(id);

        APIResponse<StudentResponse> response = APIResponse.success("Student fetched succefully", studentResponse);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getByUserName/{userName}")
    public StudentResponse getByUserName(@PathVariable String userName) {
        return studentService.getByUserName(userName);
    }
    @GetMapping("/getNameById/{userName}")
    public StudentDTO getNameById(@PathVariable String userName) {
        return studentService.getNameByUserName(userName);
    }

    @PutMapping("/update/{id}")
    public Student updateStudentProfile(@PathVariable long id, @RequestBody Student student){
        return studentService.updateStudentProfileService(id,student);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteStudentProfile(@PathVariable long id){
        studentService.deleteStudentProfileService(id);
    }

    @GetMapping("/pageination")
    public Page<Student> getAllStudents(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        return studentService.getAllStudentService(page,size);
    }

    @GetMapping("/pageination/studentDetails")
    public List<Student> getAllDetailsStudents(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        return studentService.getAllDetailStudentService(page,size);
    }
}

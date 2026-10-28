package com.example.service;

import com.example.entity.Student;
import com.example.feignclients.AddressFeignClient;
import com.example.repository.StudentRepository;
import com.example.request.CreateStudentRequest;
import com.example.response.AddressResponse;
import com.example.response.StudentPageResponse;
import com.example.response.StudentResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;

@Service
public class StudentService {

    Logger logger = LoggerFactory.getLogger(StudentService.class);

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    WebClient webClient;

    @Autowired
    AddressFeignClient addressFeignClient;

    @Autowired
    CommonService commonService;

    private static final Set<String> ALLOWED_SORT_FIELDS = Set.of("id", "firstName", "lastname", "email");

    public StudentResponse cretaeStudent(CreateStudentRequest createStudentRequest) {

        Student student = new Student();
        student.setFirstName(createStudentRequest.getFirstName());
        student.setLastName(createStudentRequest.getLastName());
        student.setEmail(createStudentRequest.getEmail());

        student.setAddressId(createStudentRequest.getAddressId());
        student = studentRepository.save(student);

        StudentResponse studentResponse = new StudentResponse(student);
        studentResponse.setAddressResponse(commonService.getAddressById(student.getAddressId()));

        // studentResponse.setAddressResponse(addressFeignClient.getById(student.getAddressId()));

        return studentResponse;
    }
/*
    public StudentResponse getById (long id) {
        Student student = studentRepository.findById(id).get();
        StudentResponse studentResponse = new StudentResponse(student);
        // studentResponse.setAddressResponse(getAddressById(student.getAddressId()));

        studentResponse.setAddressResponse(addressFeignClient.getById(student.getAddressId()));

        return studentResponse;
    }
// using webclient
    public AddressResponse getAddressById(long addressId){
       Mono<AddressResponse> addressResponseMono=  webClient.get().uri("/getById/" + addressId).retrieve().bodyToMono(AddressResponse.class);

       return addressResponseMono.block();

    }

 */

    public StudentResponse getById (long id) {

        logger.info("Inside Student getById");

        Student student = studentRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException
                        (HttpStatus.NOT_FOUND, "Student not found with id: " + id));
        StudentResponse studentResponse = new StudentResponse(student);

        studentResponse.setAddressResponse(commonService.getAddressById(student.getAddressId()));

       // studentResponse.setAddressResponse(addressFeignClient.getById(student.getAddressId()));

        return studentResponse;
    }

    public List<StudentResponse> getAllStudentDetails(){
        // step 1: Fetch all students from the database
        List<Student> students = studentRepository.findAll();
        // Step 2: Convert each student into studentResponse
        // The expression StudentResponse::new is a constructor reference. It is a shorter way of writing:  .map(student -> new StudentResponse(student))
        List<StudentResponse> studentResponses = students.stream().map(StudentResponse::new).toList();
        //step 3: Return the list of response objects
        return studentResponses;
    }



 // Implementing Pagination concept

    public StudentPageResponse getAllStudentByPagination(int page, int size){

       Pageable pageable = PageRequest.of(page,size);
       Page<Student>studentPage = studentRepository.findAll(pageable);
       List<Student> students = studentPage.getContent();

       List<StudentResponse> responses = students.stream().map(student -> {
           StudentResponse response = new StudentResponse(student);
           response.setAddressResponse(
                   commonService.getAddressById(student.getAddressId())
           );
           return response;
       }).toList();

        return new StudentPageResponse(
                responses,
                studentPage.getNumber(),
                studentPage.getSize(),
                studentPage.getTotalElements(),
                studentPage.getTotalPages(),
                studentPage.isLast()
        );
    }












/*
    @CircuitBreaker(name = "addressService",
            fallbackMethod = "fallbackGetAddressById")
    public AddressResponse getAddressById(long addressId){
       AddressResponse addressResponse = addressFeignClient.getById(addressId);

       return addressResponse;

    }

    public AddressResponse fallbackGetAddressById(long addressId, Throwable th){
        return new AddressResponse();
    }
 */

}

package com.example.service;

import com.example.entity.Student;
import com.example.feignclients.AddressFeignClient;
import com.example.repository.StudentRepository;
import com.example.request.CreateStudentRequest;
import com.example.response.AddressResponse;
import com.example.response.StudentResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

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

        Student student = studentRepository.findById(id).get();
        StudentResponse studentResponse = new StudentResponse(student);

        studentResponse.setAddressResponse(commonService.getAddressById(student.getAddressId()));

       // studentResponse.setAddressResponse(addressFeignClient.getById(student.getAddressId()));

        return studentResponse;
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

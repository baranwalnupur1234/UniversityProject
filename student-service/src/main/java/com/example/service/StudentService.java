package com.example.service;

import com.example.entity.Student;
import com.example.feignclients.AddressFeignClient;
import com.example.repository.StudentRepository;
import com.example.request.CreateStudentRequest;
import com.example.response.AddressResponse;
import com.example.response.StudentDTO;
import com.example.response.StudentResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.NOT_FOUND;

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


    public StudentResponse createStudent(CreateStudentRequest createStudentRequest) {

        Student student = new Student();
        student.setFirstName(createStudentRequest.getFirstName());
        student.setLastName(createStudentRequest.getLastName());
        student.setEmail(createStudentRequest.getEmail());
        student.setUserName(createStudentRequest.getUserName());

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
        if(id<10){
            logger.info("Inside Student getById id<10");
            throw new RuntimeException("Student not found");
        }
        Student student = studentRepository.findById(id).get();
        StudentResponse studentResponse = new StudentResponse(student);

        studentResponse.setAddressResponse(commonService.getAddressById(student.getAddressId()));

       // studentResponse.setAddressResponse(addressFeignClient.getById(student.getAddressId()));

        return studentResponse;
    }

    public StudentResponse getByUserName(String userName) {
        Student student = studentRepository.findByUserName(userName)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Student not found"));
        StudentResponse studentResponse = new StudentResponse(student);
        studentResponse.setAddressResponse(commonService.getAddressById(student.getAddressId()));

        return studentResponse;
    }
    public StudentDTO getNameByUserName(String userName) {
        Student student = studentRepository.findByUserName(userName)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Student not found"));
        StudentDTO studentDTO = new StudentDTO(student.getFirstName(), student.getLastName());
        return studentDTO;
    }

    public Student updateStudentProfileService(Long id, Student student){

        Student st = studentRepository.findById(id).orElse(null);
        if(st != null){
            st.setFirstName(student.getFirstName());
            st.setLastName(student.getLastName());
            st.setEmail(student.getEmail());
            st.setUserName(student.getUserName());
            st.setAddressId(student.getAddressId());

            return studentRepository.save(st);
        }else{
            throw new RuntimeException("Student is not found with this id : "+ id);
        }
    }

    public void deleteStudentProfileService(Long id){
        studentRepository.deleteById(id);
    }


    /*
    pagination concept building:

        Page<Student> : Contains the student records and pagination information, such as total pages and total elements.
     */
    public Page<Student> getAllStudentService(int page, int size){
        Pageable pageable = PageRequest.of(page,size); // create pagination request

        return studentRepository.findAll(pageable);  // fetches only the requested page.
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

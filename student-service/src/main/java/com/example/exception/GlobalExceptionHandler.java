package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
/**
 * GlobalExceptionHandler is a class that handles exceptions thrown by the application
 * and provides a consistent response structure.
 * It uses the @RestControllerAdvice annotation
 * to indicate that it is a global exception handler for REST controllers.
 *
 * What do the annotations mean?
 * Annotation	Purpose
 * @RestControllerAdvice Makes this class available to handle exceptions across controllers.
 * @ExceptionHandler(StudentNotFoundException.class) Specifies which exception this method handles.
 * ResponseEntity	Lets you control both the HTTP status and response body.
 * HttpStatus.NOT_FOUND	Returns HTTP status 404.
 * Notice that the handler doesn't search for the student.
 * Its responsibility is to convert the thrown exception into a suitable HTTP response.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleStudentNOtFound(StudentNotFoundException ex){

        Map<String,String> error = Map.of(
                "error", "Student Not Found",
                "message", ex.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }
}

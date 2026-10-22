package com.example.exception;

import com.example.response.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
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
    public ResponseEntity<APIResponse<Void>> handleStudentNOtFound(StudentNotFoundException ex){

       APIResponse<Void> error = APIResponse.error(
               "Student Not Found",
                 List.of(ex.getMessage())
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIResponse<Void>> handleValidation(MethodArgumentNotValidException ex) {

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField()
                       + ": " + error.getDefaultMessage())
                .toList();

        APIResponse<Void> response =
                APIResponse.error("Validation failed", errors);
        return ResponseEntity.badRequest().body(response);
    }
}

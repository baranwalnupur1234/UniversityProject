package com.example.exception;
/*
What does this code mean?
- RuntimeException: makes your exception an unchecked exception.
- StudentNotFoundException: your own meaningful exception class.
- super(message): passes the error message to the parent exception class.
For example, you can throw this exception when student ID 10000 does not exist.
 */
public class StudentNotFoundException extends RuntimeException {

    public StudentNotFoundException(String message){
        super(message);
    }
}

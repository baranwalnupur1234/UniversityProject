package com.example.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateStudentRequest {
    private String firstName;

    private String lastName;

    private String email;

    private String userName;

    private long addressId;
}

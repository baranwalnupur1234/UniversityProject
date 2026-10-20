package com.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "student")
@Getter
@Setter
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @NotBlank(message = "Student fields cannot be empty")
    @Column(name = "first_name")
    private String firstName;
    @NotBlank(message = "Student fields cannot be empty")
    @Column(name = "last_name")
    private String lastName;
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email cannot be empty")
    @Column(name = "email")
    private String email;

    @NotBlank(message = "Username cannot be empty")
    @Column(name = "user_name")
    private String userName;

    @Column(name = "address_id")
    private long addressId;
}

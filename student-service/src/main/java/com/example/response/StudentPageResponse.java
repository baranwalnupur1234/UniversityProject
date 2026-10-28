package com.example.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
public class StudentPageResponse {

    List<StudentResponse> students;
    int pageNumber;
    int pageSize;
    long totalElements;
    int totalPages;
    boolean last;


}

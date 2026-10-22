package com.example.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class APIResponse<T> {

    private boolean success;
    private String message;
    private T data;
    private LocalDateTime timestamp;
    private List<String> errors;

    // for successfull API response
    public static <T> APIResponse<T> success(String message, T data) {
        return new APIResponse<>(true, message, data, LocalDateTime.now(), null);
    }

    //for failed API responses
    public static <T> APIResponse error(String message, List<String>errors){
        return new APIResponse<>(
               false,
                message,
                null,
                LocalDateTime.now(),
                errors
        );
    }

}

package com.example.belajarcrudspring.util;

import com.example.belajarcrudspring.domain.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public class ResponseUtil {

    public ResponseUtil() {
    }

    public static <T>ResponseEntity<Object> build(Boolean status,int statusCode, String message,  T data,
                                                  HttpStatus httpStatus) {
        return new ResponseEntity<>(build(status, statusCode ,message, data), httpStatus);
    }

    private static <T>ApiResponse<T> build(Boolean status, int statusCode, String message,  T data) {
        ApiResponse response = new ApiResponse();
                response.setStatus(status);
                response.setMessage(message);
                response.setStatusCode(statusCode);
                response.setData(data);

        return  response;
    }



}

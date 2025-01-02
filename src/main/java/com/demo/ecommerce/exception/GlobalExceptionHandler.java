package com.demo.ecommerce.exception;

import com.demo.ecommerce.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFound(ResourceNotFoundException e) {
        return ResponseEntity.status(NOT_FOUND)
                .body(new ApiResponse(e.getMessage(), null));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> unexpectedException(Exception e) {
        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .body(new ApiResponse("An unexpected error occurred", null));
    }
}

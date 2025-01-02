package com.demo.ecommerce.exception;

import com.demo.ecommerce.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFound(ResourceNotFoundException e) {
        return ResponseEntity.status(NOT_FOUND)
                .body(new ApiResponse(e.getMessage(), null));
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ApiResponse> alreadyExists(AlreadyExistsException e) {
        return ResponseEntity.status(CONFLICT)
                .body(new ApiResponse(e.getMessage(), null));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> unexpectedException(Exception e) {
//        log.error(e.getMessage(), e);
        String errorMessage = "An error of type " + e.getClass().getSimpleName() + " occurred: " + e.getMessage();

        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .body(new ApiResponse(errorMessage, null));
    }
}

package com.demo.ecommerce.exception;

import com.demo.ecommerce.dto.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.services.s3.model.S3Exception;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(S3Exception.class)
    public ResponseEntity<ApiResponse> handleS3Exception(S3Exception e) {
        String errorMessage = "S3 Exception: " + e.awsErrorDetails().errorMessage();
        return ResponseEntity.status(e.statusCode())
                .body(new ApiResponse(errorMessage, null));
    }


    @ExceptionHandler(SdkClientException.class)
    public ResponseEntity<ApiResponse> handleSdkClientException(SdkClientException e) {
        String errorMessage = "AWS SDK Error: " + e.getMessage();
        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .body(new ApiResponse(errorMessage, null));
    }


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
        String errorMessage = "Error of type " + e.getClass().getSimpleName() + " : " + e.getMessage();

        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .body(new ApiResponse(errorMessage, null));
    }
}

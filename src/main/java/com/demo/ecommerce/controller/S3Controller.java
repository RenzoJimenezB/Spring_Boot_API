package com.demo.ecommerce.controller;

import com.demo.ecommerce.service.s3.S3Service;
import com.demo.ecommerce.dto.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/s3")
public class S3Controller {

    private final S3Service s3Service;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;


    @PostMapping("/{productId}")
    public ResponseEntity<ApiResponse> uploadFile(
            @RequestParam MultipartFile file,
            @PathVariable Long productId
    ) throws IOException {

        String key = file.getOriginalFilename();

        Path tempFilePath = Paths.get(System.getProperty("java.io.tmpdir"), file.getOriginalFilename());
        file.transferTo(tempFilePath);

        ApiResponse uploadResponse = s3Service.uploadFile(bucketName, key, tempFilePath, productId);
        return ResponseEntity.ok(uploadResponse);
    }


    @GetMapping("/list")
    public ResponseEntity<ApiResponse> listFiles() {
        ApiResponse listResponse = s3Service.listFiles(bucketName);
        return ResponseEntity.ok(listResponse);
    }


    @DeleteMapping
    public ResponseEntity<ApiResponse> deleteFile(@RequestParam("key") String key) {

        ApiResponse deleteResponse = s3Service.deleteFile(bucketName, key);
        return ResponseEntity.ok(deleteResponse);
    }
}

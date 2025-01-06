package com.demo.ecommerce.controller;

import com.demo.ecommerce.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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

    @PostMapping
    public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        String key = file.getOriginalFilename();

        Path tempFile = Paths.get(System.getProperty("java.io.tmpdir"), file.getOriginalFilename());
        file.transferTo(tempFile);

        return s3Service.uploadFile(bucketName, key, tempFile);
    }

    @DeleteMapping
    public String deleteFile(@RequestParam("key") String key) {
        return s3Service.deleteFile(bucketName, key);
    }

    @GetMapping("/list")
    public void listFiles() {
        s3Service.listFiles(bucketName);
    }
}

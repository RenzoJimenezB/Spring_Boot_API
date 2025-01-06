package com.demo.ecommerce;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;

import java.nio.file.Path;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final S3Client s3Client;
}

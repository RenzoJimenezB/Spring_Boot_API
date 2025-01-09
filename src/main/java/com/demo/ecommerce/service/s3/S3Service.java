package com.demo.ecommerce.service.s3;

import com.demo.ecommerce.response.ApiResponse;
import com.demo.ecommerce.service.image.IImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.nio.file.Path;
import java.util.List;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final S3Client s3Client;
    private final IImageService imageService;


    public ResponseEntity<ApiResponse> uploadFile(
            String bucketName,
            String key,
            Path filePath,
            Long productId
    ) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromFile(filePath));
        return imageService.addImage(key, productId);
    }


    public ResponseEntity<ApiResponse> deleteFile(String bucketName, String key) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        s3Client.deleteObject(deleteObjectRequest);
        return imageService.deleteImage(key);
    }

    
    public ApiResponse listFiles(String bucketName) {
        ListObjectsV2Request listObjectsRequest = ListObjectsV2Request.builder()
                .bucket(bucketName)
                .build();

        ListObjectsV2Response listObjectsResponse = s3Client.listObjectsV2(listObjectsRequest);
        List<String> fileNames = listObjectsResponse.contents().stream()
                .map(S3Object::key)
                .toList();

        return new ApiResponse("Files listed successfully", fileNames);
    }
}

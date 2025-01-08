package com.demo.ecommerce;

import com.demo.ecommerce.response.ApiResponse;
import lombok.RequiredArgsConstructor;
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

    public ApiResponse uploadFile(String bucketName, String key, Path filePath) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromFile(filePath));

        return new ApiResponse("File uploaded successfully", key);
    }


    public ApiResponse downloadFile(String bucketName, String key, Path destination) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        s3Client.getObject(getObjectRequest, destination);

        return new ApiResponse("File downloaded successfully", destination.toString());
    }


    public ApiResponse deleteFile(String bucketName, String key) {
        // check if the file exists
//        HeadObjectRequest headObjectRequest = HeadObjectRequest.builder()
//                .bucket(bucketName)
//                .key(key)
//                .build();

        // throw an exception it the object doesn't exist
//        s3Client.headObject(headObjectRequest);

        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        s3Client.deleteObject(deleteObjectRequest);

        return new ApiResponse("File deleted successfully", key);
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

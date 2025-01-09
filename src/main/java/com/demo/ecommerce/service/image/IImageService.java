package com.demo.ecommerce.service.image;

import com.demo.ecommerce.response.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface IImageService {
    ResponseEntity<ApiResponse> addImage(String imageKey, Long productId);

    ResponseEntity<ApiResponse> getImagesByProductId(Long productId);

    ResponseEntity<ApiResponse> deleteImage(String imageKey);
}

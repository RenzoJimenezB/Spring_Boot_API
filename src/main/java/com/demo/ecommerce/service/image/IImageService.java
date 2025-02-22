package com.demo.ecommerce.service.image;

import com.demo.ecommerce.dto.response.ApiResponse;

public interface IImageService {
    ApiResponse addImage(String imageKey, Long productId);

    ApiResponse getImagesByProductId(Long productId);

    ApiResponse deleteImage(String imageKey);
}

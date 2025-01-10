package com.demo.ecommerce.controller;

import com.demo.ecommerce.response.ApiResponse;
import com.demo.ecommerce.service.image.IImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/images")
public class ImageController {


    private final IImageService imageService;


    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse> getProductImages(@PathVariable Long productId) {
        ApiResponse response = imageService.getImagesByProductId(productId);
        return ResponseEntity.ok(response);
    }
}
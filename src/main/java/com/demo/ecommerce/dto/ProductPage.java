package com.demo.ecommerce.dto;

import com.demo.ecommerce.dto.response.ProductResponse;

import java.util.List;

public record ProductPage(
        int pageNumber,
        int pageSize,
        int totalPages,
        long totalElements,
        List<ProductResponse> content) {
}

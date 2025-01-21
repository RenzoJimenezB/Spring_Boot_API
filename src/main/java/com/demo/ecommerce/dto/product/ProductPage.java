package com.demo.ecommerce.dto.product;

import java.util.List;

public record ProductPage(
        int pageNumber,
        int pageSize,
        int totalPages,
        long totalElements,
        List<ProductResponse> content) {
}

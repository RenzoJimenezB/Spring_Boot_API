package com.demo.ecommerce.dto.category;

import com.demo.ecommerce.dto.product.ProductSummary;

import java.util.List;

public record CategoryResponse(
        Long id,
        String name,
        List<ProductSummary> products) {
}

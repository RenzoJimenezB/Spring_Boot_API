package com.demo.ecommerce.dto;

import java.math.BigDecimal;
import java.util.List;

public record ProductResponse(
        Long id,
        String name,
        String brand,
        BigDecimal price,
        int stock,
        String description,
        String category,
        List<String> imageUrls) {
}

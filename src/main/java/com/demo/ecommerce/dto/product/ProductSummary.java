package com.demo.ecommerce.dto.product;

import lombok.Builder;

@Builder
public record ProductSummary(
        Long id,
        String name,
        String brand) {
}

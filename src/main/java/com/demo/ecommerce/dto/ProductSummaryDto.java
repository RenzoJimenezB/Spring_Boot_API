package com.demo.ecommerce.dto;

import lombok.Builder;

@Builder
public record ProductSummaryDto(
        Long id,
        String name,
        String brand) {
}

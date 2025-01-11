package com.demo.ecommerce.dto.response;

import com.demo.ecommerce.dto.ProductSummaryDto;

import java.util.List;

public record CategoryResponse(
        Long id,
        String name,
        List<ProductSummaryDto> products) {
}

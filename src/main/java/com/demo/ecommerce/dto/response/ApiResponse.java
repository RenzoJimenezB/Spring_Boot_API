package com.demo.ecommerce.dto.response;

public record ApiResponse(
        String message,
        Object data) {
}

package com.demo.ecommerce.response;


public record ApiResponse(
        String message,
        Object data) {
}

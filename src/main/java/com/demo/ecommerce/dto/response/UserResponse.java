package com.demo.ecommerce.dto.response;

import com.demo.ecommerce.enums.Role;

public record UserResponse(
        Long id,
        String name,
        String lastName,
        String email,
        String phone,
        Role role) {
}

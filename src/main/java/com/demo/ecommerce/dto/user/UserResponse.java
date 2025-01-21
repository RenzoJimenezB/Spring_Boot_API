package com.demo.ecommerce.dto.user;

import com.demo.ecommerce.enums.Role;

public record UserResponse(
        Long id,
        String name,
        String lastName,
        String email,
        String phone,
        Role role) {
}

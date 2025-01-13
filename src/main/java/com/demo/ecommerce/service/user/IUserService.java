package com.demo.ecommerce.service.user;

import com.demo.ecommerce.dto.response.UserResponse;

public interface IUserService {

    UserResponse getUserByEmail(String email);
}

package com.demo.ecommerce.service.user;

import com.demo.ecommerce.dto.user.UserResponse;

public interface IUserService {

    UserResponse getUserByEmail(String email);
}

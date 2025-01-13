package com.demo.ecommerce.controller;

import com.demo.ecommerce.dto.response.ApiResponse;
import com.demo.ecommerce.dto.response.UserResponse;
import com.demo.ecommerce.mapper.UserMapper;
import com.demo.ecommerce.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix/users")
public class UserController {


    private final IUserService userService;
    private final UserMapper userMapper;


    @GetMapping("/{email}")
    public ResponseEntity<ApiResponse> getUserByEmail(@PathVariable String email) {
        UserResponse user = userService.getUserByEmail(email);
        return ResponseEntity.ok(new ApiResponse("Success", user));
    }
}

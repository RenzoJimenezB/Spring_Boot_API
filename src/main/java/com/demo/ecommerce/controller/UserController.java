package com.demo.ecommerce.controller;

import com.demo.ecommerce.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix/users")
public class UserController {

    private final IUserService userService;


}

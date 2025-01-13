package com.demo.ecommerce.service.user;

import com.demo.ecommerce.dto.response.UserResponse;
import com.demo.ecommerce.exception.ResourceNotFoundException;
import com.demo.ecommerce.mapper.UserMapper;
import com.demo.ecommerce.model.User;
import com.demo.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService implements IUserService {


    private final UserRepository userRepository;
    private final UserMapper userMapper;


    @Override
    public UserResponse getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return userMapper.toUserResponse(user);
    }
}
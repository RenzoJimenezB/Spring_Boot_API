package com.demo.ecommerce.mapper;

import com.demo.ecommerce.dto.response.UserResponse;
import com.demo.ecommerce.model.User;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface UserMapper {


    UserResponse toUserResponse(User user);
}

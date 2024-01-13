package com.ecommerce.userservice.service;

import com.ecommerce.userservice.dto.UserDto;
import com.ecommerce.userservice.entity.User;

import java.util.List;

public interface UserService {

    void createUser(UserDto userDto);

    User findById(Long userId);

    UserDto getUserById(Long userId);

    List<UserDto> getAllUsers();

    void updateUser(Long userId, UserDto userDto);

    void deleteUser(Long userId);
}

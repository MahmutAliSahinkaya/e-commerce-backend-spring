package com.ecommerce.userservice.service.impl;

import com.ecommerce.userservice.dto.UserDto;
import com.ecommerce.userservice.entity.User;
import com.ecommerce.userservice.exception.UserAlreadyExistsException;
import com.ecommerce.userservice.exception.UserNotFoundException;
import com.ecommerce.userservice.mapper.UserMapper;
import com.ecommerce.userservice.repository.UserRepository;
import com.ecommerce.userservice.service.UserService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(UserDto userDto) {
        log.info("Creating user with username: {}", userDto.username());
        User user = UserMapper.maptoUser(userDto, new User());
        userRepository.findByPhone(userDto.phone())
                .ifPresent(u -> {
                    throw new UserAlreadyExistsException("User already registered with given phone number: " + userDto.phone());
                });
        userRepository.save(user);
        log.info("User created with username: {}", userDto.username());
    }

    public User findById(Long userId) {
        log.info("Getting user by id: {}", userId);
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with userId: " + userId));
    }

    @Override
    public UserDto getUserById(Long userId) {
        log.info("Getting user by id: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with userId: " + userId));
        return UserMapper.toDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        log.info("Getting all users");
        return userRepository.findAll().stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }


    @Override
    public void updateUser(Long userId, UserDto userDto) {
        log.info("Updating user with id: {}", userId);
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        UserMapper.maptoUser(userDto, existingUser);
        userRepository.save(existingUser);

        log.info("User updated with id: {}", userId);
    }

    @Override
    public void deleteUser(Long userId) {
        log.info("Deleting user with id: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
        userRepository.delete(user);
        log.info("User deleted with id: {}", userId);
    }
}

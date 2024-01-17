package com.ecommerce.userservice.service.impl;

import com.ecommerce.userservice.dto.UserDto;
import com.ecommerce.userservice.entity.User;
import com.ecommerce.userservice.exception.UserAlreadyExistsException;
import com.ecommerce.userservice.exception.UserNotFoundException;
import com.ecommerce.userservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        user = getMockUser();
        userDto = getMockUserDto();
    }

    @Test
    @DisplayName("Create User - Success")
    void shouldCreateUserSuccessfully() {
        when(userRepository.findByPhone(userDto.phone())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.createUser(userDto);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Create User - Failure")
    void shouldNotCreateUser() {
        when(userRepository.findByPhone(userDto.phone())).thenReturn(Optional.of(user));

        assertThrows(UserAlreadyExistsException.class, () -> userService.createUser(userDto));
        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    @DisplayName("Find User By Id - Success")
    void shouldFindUserByIdSuccessfully() {
        when(userRepository.findById(user.getUserId())).thenReturn(Optional.of(user));

        User foundUser = userService.findById(user.getUserId());

        assertEquals(user.getUserId(), foundUser.getUserId());
        verify(userRepository, times(1)).findById(user.getUserId());
    }

    @Test
    @DisplayName("Find User By Id - Failure")
    void shouldNotFindUserById() {
        when(userRepository.findById(user.getUserId())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.findById(user.getUserId()));
        verify(userRepository, times(1)).findById(user.getUserId());
    }

    @Test
    @DisplayName("Get User By Id - Success")
    void shouldGetUserByIdSuccessfully() {
        when(userRepository.findById(user.getUserId())).thenReturn(Optional.of(user));

        UserDto foundUserDto = userService.getUserById(user.getUserId());

        assertEquals(user.getUserId(), foundUserDto.userId());
        verify(userRepository, times(1)).findById(user.getUserId());
    }

    @Test
    @DisplayName("Get User By Id - Failure")
    void shouldNotGetUserById() {
        when(userRepository.findById(user.getUserId())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserById(user.getUserId()));
        verify(userRepository, times(1)).findById(user.getUserId());
    }

    @Test
    @DisplayName("Get All Users - Success")
    void shouldGetAllUsersSuccessfully() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));

        List<UserDto> users = userService.getAllUsers();

        assertFalse(users.isEmpty());
        assertEquals(1, users.size());
        assertEquals(user.getUsername(), users.get(0).username());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Update User - Success")
    void shouldUpdateUserSuccessfully() {
        when(userRepository.findById(userDto.userId())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.updateUser(userDto.userId(), userDto);

        verify(userRepository, times(1)).findById(userDto.userId());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Delete User - Success")
    void shouldDeleteUserSuccessfully() {
        when(userRepository.findById(userDto.userId())).thenReturn(Optional.of(user));

        userService.deleteUser(userDto.userId());

        verify(userRepository, times(1)).findById(userDto.userId());
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    @DisplayName("Delete User - Failure")
    void shouldNotDeleteUser() {
        when(userRepository.findById(userDto.userId())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(userDto.userId()));
        verify(userRepository, times(1)).findById(userDto.userId());
        verify(userRepository, times(0)).delete(any(User.class));
    }

    private User getMockUser() {
        return User.builder()
                .userId(1L)
                .firstName("testFirstName")
                .lastName("testLastName")
                .username("testUsername")
                .email("test@email.com")
                .imageUrl("testImageUrl")
                .phone("0123456789")
                .addresses(Set.of())
                .build();
    }

    private UserDto getMockUserDto() {
        return UserDto.builder()
                .userId(1L)
                .firstName("testFirstName")
                .lastName("testLastName")
                .username("testUsername")
                .imageUrl("testImageUrl")
                .email("test@mail.com")
                .phone("0123456789")
                .addressDtos(Set.of())
                .build();
    }
}
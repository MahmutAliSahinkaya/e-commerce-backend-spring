package com.ecommerce.userservice.mapper;

import com.ecommerce.userservice.dto.UserDto;
import com.ecommerce.userservice.entity.User;

import java.util.stream.Collectors;

public class UserMapper {
    public static UserDto toDto(User user) {
        return UserDto.builder()
                .userId(user.getUserId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .imageUrl(user.getImageUrl())
                .email(user.getEmail())
                .phone(user.getPhone())
                .addressDtos(user.getAddresses().stream()
                        .map(AddressMapper::toDto)
                        .collect(Collectors.toSet()))
                .build();
    }

    public static User toEntity(UserDto userDto) {
        User user = new User();
        user.setUserId(userDto.userId());
        user.setFirstName(userDto.firstName());
        user.setLastName(userDto.lastName());
        user.setUsername(userDto.username());
        user.setImageUrl(userDto.imageUrl());
        user.setEmail(userDto.email());
        user.setPhone(userDto.phone());
        user.setAddresses(userDto.addressDtos().stream()
                .map(AddressMapper::toEntity)
                .collect(Collectors.toSet()));
        return user;
    }

}

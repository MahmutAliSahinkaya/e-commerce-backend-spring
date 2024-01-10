package com.ecommerce.userservice.dto;

import lombok.Builder;

import java.util.Set;

@Builder
public record UserDto(
        Long userId,
        String firstName,
        String lastName,
        String username,
        String imageUrl,
        String email,
        String phone,
        Set<AddressDto> addressDtos
) {
}

package com.ecommerce.orderservice.dto;

import lombok.Builder;

@Builder
public record UserDto(
        Long userId,
        String firstName,
        String lastName,
        String email,
        String imageUrl,
        String phone,
        CartDto cartDto
) {
}

package com.ecommerce.paymentservice.dto;

import lombok.Builder;

@Builder
public record UserDto (
        Long userId,
        String firstName,
        String lastName,
        String email,
        String password,
        String imageUrl,
        String phone
){}

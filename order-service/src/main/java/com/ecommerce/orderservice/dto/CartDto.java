package com.ecommerce.orderservice.dto;

import lombok.Builder;

import java.util.Set;

@Builder
public record CartDto(
        Long cartId,
        Long userId,
        Set<OrderDto> orderDtos,
        UserDto userDto
) {
}

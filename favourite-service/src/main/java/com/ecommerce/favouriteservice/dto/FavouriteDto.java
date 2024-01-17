package com.ecommerce.favouriteservice.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record FavouriteDto (
        Long userId,
        Long productId,
        LocalDateTime likeDate,
        UserDto userDto,
        ProductDto productDto
) {
}

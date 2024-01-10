package com.ecommerce.favouriteservice.dto;

import lombok.Builder;

import java.util.Set;

@Builder
public record UserDto (
        Long userId,
        String firstName,
        String lastName,
        String imageUrl,
        String email,
        String phone,
        Set<FavouriteDto> favouriteDtos
) {
}

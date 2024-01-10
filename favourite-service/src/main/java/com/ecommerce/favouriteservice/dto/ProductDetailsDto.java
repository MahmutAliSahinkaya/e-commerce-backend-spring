package com.ecommerce.favouriteservice.dto;

import lombok.Builder;

@Builder
public record ProductDetailsDto(
        Long productId,
        String productTitle
) {
}

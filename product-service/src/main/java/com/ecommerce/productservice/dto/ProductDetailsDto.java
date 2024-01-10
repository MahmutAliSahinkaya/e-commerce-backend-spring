package com.ecommerce.productservice.dto;

import lombok.Builder;

@Builder
public record ProductDetailsDto(
        Long productId,
        String productTitle
) {
}

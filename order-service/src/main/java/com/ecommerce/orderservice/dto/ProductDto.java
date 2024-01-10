package com.ecommerce.orderservice.dto;

import lombok.Builder;

import java.util.Set;

@Builder
public record ProductDto(
        Long productId,
        String productTitle,
        String imageUrl,
        String sku,
        Double unitPrice,
        Long quantity,
        Set<CartItemDto> cartItemDtos
) {
}

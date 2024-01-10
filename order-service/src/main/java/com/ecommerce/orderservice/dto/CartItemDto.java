package com.ecommerce.orderservice.dto;

import lombok.Builder;

@Builder
public record CartItemDto (
        Long cartItemId,
        Long productId,
        Long quantity,
        Long cartId
) {
}

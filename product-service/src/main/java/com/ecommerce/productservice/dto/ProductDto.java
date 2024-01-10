package com.ecommerce.productservice.dto;

import lombok.Builder;

@Builder
public record ProductDto (
        Long productId,
        String productTitle,
        String imageUrl,
        String sku,
        Double priceUnit,
        Integer quantity,
        CategoryDto categoryDto
) {}

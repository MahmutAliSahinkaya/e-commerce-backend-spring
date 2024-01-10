package com.ecommerce.favouriteservice.dto;

import lombok.Builder;

import java.util.Set;

@Builder
public record ProductDto (
        Long productId,
        String productTitle,
        String imageUrl,
        String sku,
        Double priceUnit,
        Long quantity,
        Set<FavouriteDto> favouriteDtos

) {
}

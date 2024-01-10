package com.ecommerce.reportservice.dto;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record OrderDto(
        Long id,
        LocalDate orderTime,
        float shippingCost,
        float productCost,
        float subtotal,
        float total,
        Long productId,
        Long categoryId
) {
}

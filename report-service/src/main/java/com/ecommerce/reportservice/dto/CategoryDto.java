package com.ecommerce.reportservice.dto;

import lombok.Builder;

@Builder
public record CategoryDto(
        Long id,
        String name
) {
}

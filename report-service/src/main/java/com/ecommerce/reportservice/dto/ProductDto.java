package com.ecommerce.reportservice.dto;

import lombok.Builder;

@Builder
public record ProductDto(
        Long id,
        String name
){
}

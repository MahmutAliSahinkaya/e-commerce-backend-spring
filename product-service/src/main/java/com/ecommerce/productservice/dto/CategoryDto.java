package com.ecommerce.productservice.dto;

import lombok.Builder;

import java.util.Set;

@Builder
public record CategoryDto (
        Long categoryId,
        String categoryTitle,
        String imageUrl,
        Set<CategoryDto> subCategories,
        Set<ProductDto> productDtos,
        CategoryDto parentCategoryDto
){}

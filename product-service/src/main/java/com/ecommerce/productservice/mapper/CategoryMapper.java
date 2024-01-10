package com.ecommerce.productservice.mapper;

import com.ecommerce.productservice.dto.CategoryDto;
import com.ecommerce.productservice.entity.Category;

import java.util.Optional;

public class CategoryMapper {

    public static CategoryDto toDto(Category category) {
        Category parentCategory = Optional.ofNullable(category.getParentCategory()).orElseGet(Category::new);

        return CategoryDto.builder()
                .categoryId(category.getCategoryId())
                .categoryTitle(category.getCategoryTitle())
                .imageUrl(category.getImageUrl())
                .parentCategoryDto(
                        CategoryDto.builder()
                                .categoryId(parentCategory.getCategoryId())
                                .categoryTitle(parentCategory.getCategoryTitle())
                                .imageUrl(parentCategory.getImageUrl())
                                .build())
                .build();
    }

    public static Category toEntity(CategoryDto categoryDto) {
        CategoryDto parentCategoryDto = categoryDto.parentCategoryDto();

        Category.CategoryBuilder categoryBuilder = Category.builder()
                .categoryId(categoryDto.categoryId())
                .categoryTitle(categoryDto.categoryTitle())
                .imageUrl(categoryDto.imageUrl());

        if (parentCategoryDto != null) {
            categoryBuilder.parentCategory(Category.builder()
                    .categoryId(parentCategoryDto.categoryId())
                    .categoryTitle(parentCategoryDto.categoryTitle())
                    .imageUrl(parentCategoryDto.imageUrl())
                    .build());
        }

        return categoryBuilder.build();
    }


}

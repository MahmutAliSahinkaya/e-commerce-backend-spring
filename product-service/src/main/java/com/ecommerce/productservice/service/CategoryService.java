package com.ecommerce.productservice.service;

import com.ecommerce.productservice.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto addCategory(CategoryDto category);

    CategoryDto updateCategory(Long categoryId, CategoryDto category);

    CategoryDto getCategoryById(Long categoryId);

    List<CategoryDto> listCategories();

    void assignProductToCategory(Long productId, Long categoryId);

    void removeProductFromCategory(Long productId, Long categoryId);

    void deleteCategory(Long categoryId);

}

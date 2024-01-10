package com.ecommerce.productservice.service.impl;

import com.ecommerce.productservice.dto.CategoryDto;
import com.ecommerce.productservice.entity.Category;
import com.ecommerce.productservice.entity.Product;
import com.ecommerce.productservice.exception.CategoryNotFoundException;
import com.ecommerce.productservice.exception.ProductNotFoundException;
import com.ecommerce.productservice.mapper.CategoryMapper;
import com.ecommerce.productservice.mapper.ProductMapper;
import com.ecommerce.productservice.repository.CategoryRepository;
import com.ecommerce.productservice.repository.ProductRepository;
import com.ecommerce.productservice.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository,
                               ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }


    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        logger.info("Adding category: {}", categoryDto);

        Optional<Category> existingCategory = categoryRepository.findByCategoryTitle(categoryDto.categoryTitle());

        if (existingCategory.isPresent()) {
            return CategoryMapper.toDto(existingCategory.get());
        }

        Category newCategory = categoryRepository.save(CategoryMapper.toEntity(categoryDto));
        return CategoryMapper.toDto(newCategory);
    }



    @Override
    public CategoryDto updateCategory(Long categoryId, CategoryDto categoryDto) {
        if (categoryDto == null) {
            throw new CategoryNotFoundException("CategoryDto cannot be null");
        }
        logger.info("Updating category with id {}: {}", categoryId, categoryDto);
        Category category = findCategoryById(categoryId);
        category.setCategoryTitle(categoryDto.categoryTitle());
        category.setImageUrl(categoryDto.imageUrl());
        category.setSubCategories(categoryDto.subCategories().stream()
                .map(CategoryMapper::toEntity)
                .collect(Collectors.toSet()));
        category.setProducts(categoryDto.productDtos().stream()
                .map(ProductMapper::toEntity)
                .collect(Collectors.toSet()));
        category.setParentCategory(CategoryMapper.toEntity(categoryDto.parentCategoryDto()));
        return CategoryMapper.toDto(categoryRepository.save(category));
    }


    @Override
    public CategoryDto getCategoryById(Long categoryId) {
        Category category = findCategoryById(categoryId);
        logger.info("Retrieved category with id: {}", categoryId);
        return CategoryMapper.toDto(category);
    }


    @Override
    public List<CategoryDto> listCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(CategoryMapper::toDto)
                .collect(Collectors.toList());
    }


    @Override
    public void assignProductToCategory(Long productId, Long categoryId) {
        logger.info("Assigning product with id {} to category with id {}", productId, categoryId);
        Category category = findCategoryById(categoryId);
        category.getProducts().add(findProductById(productId));
        categoryRepository.save(category);
    }


    @Override
    public void removeProductFromCategory(Long productId, Long categoryId) {
        logger.info("Removing product with id {} from category with id {}", productId, categoryId);
        Category category = findCategoryById(categoryId);
        category.getProducts().remove(findProductById(productId));
        categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        logger.info("Deleting category with id: {}", categoryId);
        categoryRepository.deleteById(categoryId);
    }

    private Category findCategoryById(Long categoryId) {
        logger.info("Finding category by id: {}", categoryId);
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id " + categoryId));
    }

    private Product findProductById(Long productId) {
        logger.info("Finding product by id: {}", productId);
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id " + productId));
    }

}

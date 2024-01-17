package com.ecommerce.productservice.service.impl;

import com.ecommerce.productservice.dto.CategoryDto;
import com.ecommerce.productservice.dto.ProductDto;
import com.ecommerce.productservice.entity.Category;
import com.ecommerce.productservice.entity.Product;
import com.ecommerce.productservice.exception.CategoryNotFoundException;
import com.ecommerce.productservice.mapper.CategoryMapper;
import com.ecommerce.productservice.repository.CategoryRepository;
import com.ecommerce.productservice.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private CategoryDto categoryDto;
    private ProductDto productDto;
    private Category category;
    private Product product;

    @BeforeEach
    void setUp() {
        categoryDto = getMockCategoryDto();
        productDto = getMockProductDto();
        category = getMockCategoryEntity();
        product = getMockProductEntity();
    }

    @Test
    @DisplayName("Add category - Success")
    void testAddCategory_Success() {
        when(categoryRepository.findByCategoryTitle(anyString())).thenReturn(Optional.empty());
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        CategoryDto result = categoryService.addCategory(categoryDto);

        verify(categoryRepository).save(any(Category.class));
        assertNotNull(result);
        assertEquals(categoryDto.categoryId(), result.categoryId());
    }

    @Test
    @DisplayName("Add category - Category already exists")
    void testAddCategory_CategoryAlreadyExists() {
        when(categoryRepository.findByCategoryTitle(anyString())).thenReturn(Optional.of(category));

        CategoryDto result = categoryService.addCategory(categoryDto);

        assertNotNull(result);
        assertEquals(categoryDto.categoryId(), result.categoryId());
    }

    @Test
    @DisplayName("Update category - Success")
    void shouldUpdateCategorySuccessfullyWhenCategoryDtoIsNotNull() {
        Long existingCategoryId = 1L;
        CategoryDto parentCategoryDto = new CategoryDto(2L, "parentCategoryTitle", "parentImageUrl", new HashSet<>(), new HashSet<>(), null);
        CategoryDto categoryDto = new CategoryDto(1L, "testCategoryTitle", "testimageUrl", new HashSet<>(), new HashSet<>(), parentCategoryDto);
        Category existingCategory = CategoryMapper.toEntity(categoryDto);

        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.save(any(Category.class))).thenReturn(existingCategory);

        CategoryDto result = categoryService.updateCategory(existingCategoryId, categoryDto);

        verify(categoryRepository).save(any(Category.class));
        assertNotNull(result);
        assertEquals(categoryDto.categoryId(), result.categoryId());
    }

    @Test
    @DisplayName("Update category - Failure")
    void shouldThrowCategoryNotFoundExceptionWhenCategoryNotFound() {
        Long nonExistentCategoryId = 999L;
        CategoryDto categoryDto = new CategoryDto(1L, "testCategoryTitle", "testimageUrl", new HashSet<>(), new HashSet<>(), null);

        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> categoryService.updateCategory(nonExistentCategoryId, categoryDto));
    }

    @Test
    @DisplayName("Update category - Failure")
    void shouldThrowCategoryNotFoundExceptionWhenCategoryDtoIsNull() {
        assertThrows(CategoryNotFoundException.class, () -> categoryService.updateCategory(1L, null));
    }


    @Test
    @DisplayName("Get category by id - Success")
    void testGetCategoryById_Success() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));

        CategoryDto result = categoryService.getCategoryById(1L);

        assertNotNull(result);
        assertEquals(categoryDto.categoryId(), result.categoryId());
    }

    @Test
    @DisplayName("Get category by id - Failure")
    void testGetCategoryById_Failure() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> categoryService.getCategoryById(1L));
    }

    @Test
    @DisplayName("List categories - Success")
    void testListCategories_Success() {
        when(categoryRepository.findAll()).thenReturn(Arrays.asList(category));

        var result = categoryService.listCategories();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Assign product to category - Success")
    void testAssignProductToCategory_Success() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        categoryService.assignProductToCategory(1L, 1L);

        verify(categoryRepository).save(any(Category.class));
    }

    @Test
    @DisplayName("Assign product to category - Failure")
    void testAssignProductToCategory_Failure() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> categoryService.assignProductToCategory(1L, 1L));
    }

    @Test
    @DisplayName("Remove product from category - Success")
    void testRemoveProductFromCategory_Success() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        categoryService.removeProductFromCategory(1L, 1L);

        verify(categoryRepository).save(any(Category.class));
    }

    @Test
    @DisplayName("Remove product from category - Failure")
    void testRemoveProductFromCategory_Failure() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> categoryService.removeProductFromCategory(1L, 1L));
    }

    @Test
    @DisplayName("Delete category - Success")
    void testDeleteCategory_Success() {
        doNothing().when(categoryRepository).deleteById(anyLong());

        categoryService.deleteCategory(1L);

        verify(categoryRepository).deleteById(anyLong());
    }

    @Test
    @DisplayName("Delete category - Failure")
    void testDeleteCategory_Failure() {
        doThrow(IllegalArgumentException.class).when(categoryRepository).deleteById(anyLong());

        assertThrows(IllegalArgumentException.class, () -> categoryService.deleteCategory(1L));
    }

    private CategoryDto getMockCategoryDto() {
        return CategoryDto.builder()
                .categoryId(1L)
                .categoryTitle("Test Category")
                .imageUrl("http://example.com/image.jpg")
                .subCategories(new HashSet<>())
                .productDtos(new HashSet<>())
                .parentCategoryDto(null)
                .build();
    }

    private ProductDto getMockProductDto() {
        return ProductDto.builder()
                .productId(1L)
                .productTitle("Test Product")
                .imageUrl("http://example.com/image.jpg")
                .sku("SKU123")
                .priceUnit(100.0)
                .quantity(10)
                .categoryDto(getMockCategoryDto())
                .build();
    }

    private Product getMockProductEntity() {
        return Product.builder()
                .productId(1L)
                .productTitle("Test Product")
                .imageUrl("http://example.com/image.jpg")
                .sku("SKU123")
                .priceUnit(100.0)
                .quantity(10)
                .category(getMockCategoryEntity())
                .build();
    }

    private Category getMockCategoryEntity() {
        return Category.builder()
                .categoryId(1L)
                .categoryTitle("Test Category")
                .imageUrl("http://example.com/image.jpg")
                .subCategories(new HashSet<>())
                .parentCategory(null)
                .products(new HashSet<>())
                .build();
    }
}
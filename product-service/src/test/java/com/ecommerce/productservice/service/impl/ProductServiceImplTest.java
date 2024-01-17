package com.ecommerce.productservice.service.impl;

import com.ecommerce.productservice.dto.CategoryDto;
import com.ecommerce.productservice.dto.ProductDetailsDto;
import com.ecommerce.productservice.dto.ProductDto;
import com.ecommerce.productservice.entity.Category;
import com.ecommerce.productservice.entity.Product;
import com.ecommerce.productservice.exception.ProductDetailsNotFoundException;
import com.ecommerce.productservice.exception.ProductNotFoundException;
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
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private ProductDto productDto;
    private Product product;

    @BeforeEach
    void setUp() {
        productDto = getMockProductDto();
        product = getMockProductEntity();
    }


    @Test
    @DisplayName("Update Product - Should update product when product exists")
    void updateProduct_WithExistingProduct_ShouldUpdateProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenAnswer(i -> i.getArgument(0));

        ProductDto updatedDto = productService.updateProduct(1L, productDto);

        assertNotNull(updatedDto);
        assertEquals(productDto.quantity(), updatedDto.quantity());
        assertEquals(productDto.priceUnit(), updatedDto.priceUnit());
        verify(productRepository).save(any(Product.class));
    }

    @Test
    @DisplayName("Update Product - Should throw exception when product does not exist")
    void updateProduct_WithNonExistingProduct_ShouldThrowException() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> {
            productService.updateProduct(1L, productDto);
        });
    }

    @Test
    @DisplayName("Update Product - Product Not Found - Should Throw Exception")
    void whenProductNotFound_shouldThrowException() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.updateProduct(1L, productDto));
    }

    @Test
    @DisplayName("Get Product Details By Id - Product Not Found - Should Throw Exception")
    void whenProductNotFoundForDetails_shouldThrowException() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ProductDetailsNotFoundException.class, () -> productService.getProductDetailsById(1L));
    }



    @Test
    @DisplayName("Add Product - Success")
    void testAddProduct_Success() {
        when(productRepository.findBySku(anyString())).thenReturn(Optional.empty());
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductDto result = productService.addProduct(productDto);

        verify(productRepository).save(any(Product.class));
        assertNotNull(result);
        assertEquals(productDto.productId(), result.productId());
    }

    @Test
    @DisplayName("Add Product - Failure")
    void testAddProduct_Failure() {
        when(productRepository.findBySku(anyString())).thenReturn(Optional.of(product));

        ProductDto result = productService.addProduct(productDto);

        verify(productRepository).save(any(Product.class));
        assertNotNull(result);
        assertEquals(productDto.productId(), result.productId());
    }

    @Test
    @DisplayName("Update Product - Success")
    void testUpdateProduct_Success() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductDto result = productService.updateProduct(1L, productDto);

        verify(productRepository).save(any(Product.class));
        assertNotNull(result);
        assertEquals(productDto.productId(), result.productId());
    }

    @Test
    @DisplayName("Update Product - Failure")
    void testUpdateProduct_Failure() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.updateProduct(1L, productDto));
    }

    @Test
    @DisplayName("Update Product - Failure")
    void shouldThrowProductNotFoundExceptionWhenProductDtoIsNull() {
        assertThrows(ProductNotFoundException.class, () -> productService.updateProduct(1L, null));
    }

    @Test
    @DisplayName("Get Product By Id - Success")
    void testGetProductById_Success() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        ProductDto result = productService.getProductById(1L);

        assertNotNull(result);
        assertEquals(productDto.productId(), result.productId());
    }

    @Test
    @DisplayName("Get Product By Id - Failure")
    void testGetProductById_Failure() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(1L));
    }

    @Test
    @DisplayName("Get Product Details By Id - Success")
    void testGetProductDetailsById_Success() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        ProductDetailsDto result = productService.getProductDetailsById(1L);

        assertNotNull(result);
        assertEquals(productDto.productId(), result.productId());
    }

    @Test
    @DisplayName("Get Product Details By Id - Failure")
    void testGetProductDetailsById_Failure() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ProductDetailsNotFoundException.class, () -> productService.getProductDetailsById(1L));
    }



    @Test
    @DisplayName("Get All Products - Success")
    void testGetAllProducts_Success() {
        when(productRepository.findAll()).thenReturn(Arrays.asList(product));

        var result = productService.getAllProducts();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Search Products - Success")
    void testSearchProducts_Success() {
        when(productRepository.findByProductTitleContaining(anyString())).thenReturn(Arrays.asList(product));

        var result = productService.searchProducts("Test");

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Filter Products By Category - Success")
    void testFilterProductsByCategory_Success() {
        when(productRepository.findByCategoryCategoryId(anyLong())).thenReturn(Arrays.asList(product));

        var result = productService.filterProductsByCategory(1L);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Update Stock - Success")
    void testUpdateStock_Success() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductDto result = productService.updateStock(1L, 10);

        verify(productRepository).save(any(Product.class));
        assertNotNull(result);
        assertEquals(productDto.productId(), result.productId());
    }

    @Test
    @DisplayName("Update Price - Success")
    void testUpdatePrice_Success() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductDto result = productService.updatePrice(1L, 100.0);

        verify(productRepository).save(any(Product.class));
        assertNotNull(result);
        assertEquals(productDto.productId(), result.productId());
    }

    @Test
    @DisplayName("Delete Product - Success")
    void testDeleteProduct_Success() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        productService.deleteProduct(1L);

        verify(productRepository).delete(any(Product.class));
    }

    @Test
    @DisplayName("Delete Product - Failure")
    void testDeleteProduct_Failure() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> productService.deleteProduct(1L));
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
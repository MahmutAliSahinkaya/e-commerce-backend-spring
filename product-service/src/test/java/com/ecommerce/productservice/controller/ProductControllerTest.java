package com.ecommerce.productservice.controller;

import com.ecommerce.productservice.dto.CategoryDto;
import com.ecommerce.productservice.dto.ProductDetailsDto;
import com.ecommerce.productservice.dto.ProductDto;
import com.ecommerce.productservice.exception.ProductNotFoundException;
import com.ecommerce.productservice.service.impl.ProductServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@ContextConfiguration(classes = {ProductController.class})
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductServiceImpl productService;

    @Autowired
    private ObjectMapper objectMapper;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    @DisplayName("Add Product - Should Create Product")
    void addProduct_ShouldCreateProduct() throws Exception {
        ProductDto productDto = getMockProductDto();
        when(productService.addProduct(any(ProductDto.class))).thenReturn(productDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.productId").value(productDto.productId()));
    }

    @Test
    @DisplayName("Update Product - Should Update Product")
    void updateProduct_ShouldUpdateProduct() throws Exception {
        ProductDto productDto = getMockProductDto();
        when(productService.updateProduct(anyLong(), any(ProductDto.class))).thenReturn(productDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(productDto.productId()));
    }

    @Test
    @DisplayName("Get Product By Id - Should Return Product")
    void getProductById_ShouldReturnProduct() throws Exception {
        ProductDto productDto = getMockProductDto();
        when(productService.getProductById(anyLong())).thenReturn(productDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(productDto.productId()));
    }

    @Test
    @DisplayName("Get Product By Id - Should Return Not Found When Product Does Not Exist")
    void getProductById_ShouldReturnNotFound_WhenProductDoesNotExist() throws Exception {
        when(productService.getProductById(anyLong())).thenThrow(new ProductNotFoundException("Product not found"));

        mockMvc.perform(MockMvcRequestBuilders.get("/products/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Get Product Details By Id - Should Return Product Details")
    void getProductDetailsById_ShouldReturnProductDetails() throws Exception {
        ProductDetailsDto productDetailsDto = getMockProductDetailsDto();
        when(productService.getProductDetailsById(anyLong())).thenReturn(productDetailsDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/products/1/details"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(productDetailsDto.productId()));
    }

    @Test
    @DisplayName("Get Product Details By Id - Should Return Not Found When Product Does Not Exist")
    void getProductDetailsById_ShouldReturnNotFound_WhenProductDoesNotExist() throws Exception {
        when(productService.getProductDetailsById(anyLong())).thenThrow(new ProductNotFoundException("Product not found"));

        mockMvc.perform(MockMvcRequestBuilders.get("/products/1/details"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Get All Products - Should Return Products")
    void getAllProducts_ShouldReturnProducts() throws Exception {
        List<ProductDto> productDtos = Arrays.asList(getMockProductDto(), getMockProductDto());
        when(productService.getAllProducts()).thenReturn(productDtos);

        mockMvc.perform(MockMvcRequestBuilders.get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(productDtos.size()));
    }

    @Test
    @DisplayName("Search Products - Should Return Products")
    void searchProducts_ShouldReturnProducts() throws Exception {
        List<ProductDto> productDtos = Arrays.asList(getMockProductDto(), getMockProductDto());
        when(productService.searchProducts(anyString())).thenReturn(productDtos);

        mockMvc.perform(MockMvcRequestBuilders.get("/products/search")
                        .param("keyword", "test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(productDtos.size()));
    }

    @Test
    @DisplayName("Filter Products By Category - Should Return Products")
    void filterProductsByCategory_ShouldReturnProducts() throws Exception {
        List<ProductDto> productDtos = Arrays.asList(getMockProductDto(), getMockProductDto());
        when(productService.filterProductsByCategory(anyLong())).thenReturn(productDtos);

        mockMvc.perform(MockMvcRequestBuilders.get("/products/filter")
                        .param("categoryId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(productDtos.size()));
    }

    @Test
    @DisplayName("Update Stock - Should Update Product Stock")
    void updateStock_ShouldUpdateProductStock() throws Exception {
        ProductDto productDto = getMockProductDto();
        when(productService.updateStock(anyLong(), anyInt())).thenReturn(productDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/products/1/stock")
                        .param("quantity", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(productDto.productId()));
    }

    @Test
    @DisplayName("Update Stock - Should Return Not Found When Product Does Not Exist")
    void updateStock_ShouldReturnNotFound_WhenProductDoesNotExist() throws Exception {
        when(productService.updateStock(anyLong(), anyInt())).thenThrow(new ProductNotFoundException("Product not found"));

        mockMvc.perform(MockMvcRequestBuilders.put("/products/1/stock")
                        .param("quantity", "10"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Update Price - Should Update Product Price")
    void updatePrice_ShouldUpdateProductPrice() throws Exception {
        ProductDto productDto = getMockProductDto();
        when(productService.updatePrice(anyLong(), anyDouble())).thenReturn(productDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/products/1/price")
                        .param("price", "100.0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(productDto.productId()));
    }

    @Test
    @DisplayName("Update Price - Should Return Not Found When Product Does Not Exist")
    void updatePrice_ShouldReturnNotFound_WhenProductDoesNotExist() throws Exception {
        when(productService.updatePrice(anyLong(), anyDouble())).thenThrow(new ProductNotFoundException("Product not found"));

        mockMvc.perform(MockMvcRequestBuilders.put("/products/1/price")
                        .param("price", "100.0"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Delete Product - Should Return No Content")
    void deleteProduct_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/products/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Delete Product - Should Return Not Found When Product Does Not Exist")
    void deleteProduct_ShouldReturnNotFound_WhenProductDoesNotExist() throws Exception {
        doThrow(new ProductNotFoundException("Product not found")).when(productService).deleteProduct(anyLong());

        mockMvc.perform(MockMvcRequestBuilders.delete("/products/1"))
                .andExpect(status().isNotFound());
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

    private ProductDetailsDto getMockProductDetailsDto() {
        return ProductDetailsDto.builder()
                .productId(1L)
                .productTitle("Test Product")
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
}
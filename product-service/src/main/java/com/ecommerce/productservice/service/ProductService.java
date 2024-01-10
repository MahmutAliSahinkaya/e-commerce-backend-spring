package com.ecommerce.productservice.service;

import com.ecommerce.productservice.dto.ProductDetailsDto;
import com.ecommerce.productservice.dto.ProductDto;

import java.util.List;

public interface ProductService {

    ProductDto addProduct(ProductDto productDto);

    ProductDto updateProduct(Long productId, ProductDto productDto);

    ProductDto getProductById(Long productId);

    ProductDetailsDto getProductDetailsById(Long productId);

    List<ProductDto> getAllProducts();

    List<ProductDto> searchProducts(String keyword);

    List<ProductDto> filterProductsByCategory(Long categoryId);

    ProductDto updateStock(Long productId, int quantity);

    ProductDto updatePrice(Long productId, Double price);

    void deleteProduct(Long productId);


}

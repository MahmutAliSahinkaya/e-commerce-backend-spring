package com.ecommerce.reportservice.service.fallback;

import com.ecommerce.reportservice.dto.CategoryDto;
import com.ecommerce.reportservice.dto.ProductDto;
import com.ecommerce.reportservice.service.client.ProductServiceClient;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class ProductFallback implements ProductServiceClient {

    @Override
    public ProductDto getProductById(Long productId) {
        return getProductByIdFallback(new Exception("Product Service is down"));
    }

    @Override
    public CategoryDto getCategoryById(Long categoryId) {
        return getCategoryByIdFallback(new Exception("Product Service is down"));
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return getAllProductsFallback(new Exception("Product Service is down"));
    }

    private ProductDto getProductByIdFallback(Throwable throwable) {
        System.out.println(throwable.getMessage());
        return null;
    }

    private CategoryDto getCategoryByIdFallback(Throwable throwable) {
        System.out.println(throwable.getMessage());
        return null;
    }

    private List<ProductDto> getAllProductsFallback(Throwable throwable) {
        System.out.println(throwable.getMessage());
        return Collections.emptyList();
    }
}

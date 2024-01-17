package com.ecommerce.reportservice.service.client;

import com.ecommerce.reportservice.dto.CategoryDto;
import com.ecommerce.reportservice.dto.ProductDto;
import com.ecommerce.reportservice.service.fallback.ProductFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Primary
@FeignClient(name = "product-service", url = "http://localhost:8081", fallback = ProductFallback.class)
public interface ProductServiceClient {

    @GetMapping(value = "/products/{productId}", consumes = "application/json")
    ProductDto getProductById(@PathVariable("productId") Long productId);


    @GetMapping(value = "/categories/{categoryId}", consumes = "application/json")
    CategoryDto getCategoryById(@PathVariable("categoryId") Long categoryId);


    @GetMapping(value = "/products", consumes = "application/json")
    List<ProductDto> getAllProducts();

}

package com.ecommerce.reportservice.service.client;

import com.ecommerce.reportservice.dto.CategoryDto;
import com.ecommerce.reportservice.dto.ProductDto;
import com.ecommerce.reportservice.service.fallback.ProductFallback;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Primary
@FeignClient(name = "product-service", url = "http://localhost:8081", fallback = ProductFallback.class)
public interface ProductServiceClient {

    @Retry(name = "getProductById", fallbackMethod = "getProductByIdFallback")
    @RateLimiter(name = "getProductById", fallbackMethod = "getProductByIdFallback")
    @GetMapping(value = "/products/{productId}", consumes = "application/json")
    ProductDto getProductById(@PathVariable("productId") Long productId);


    @Retry(name = "getCategoryById", fallbackMethod = "getCategoryByIdFallback")
    @RateLimiter(name = "getCategoryById", fallbackMethod = "getCategoryByIdFallback")
    @GetMapping(value = "/categories/{categoryId}", consumes = "application/json")
    CategoryDto getCategoryById(@PathVariable("categoryId") Long categoryId);


    @Retry(name = "getAllProducts", fallbackMethod = "getAllProductsFallback")
    @RateLimiter(name = "getAllProducts", fallbackMethod = "getAllProductsFallback")
    @GetMapping(value = "/products", consumes = "application/json")
    List<ProductDto> getAllProducts();

}

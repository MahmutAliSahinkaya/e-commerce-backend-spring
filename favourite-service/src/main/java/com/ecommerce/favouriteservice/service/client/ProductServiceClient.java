package com.ecommerce.favouriteservice.service.client;

import com.ecommerce.favouriteservice.dto.ProductDetailsDto;
import com.ecommerce.favouriteservice.dto.ProductDto;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Primary
@FeignClient(name = "product-service", url = "http://localhost:8081", fallback = ProductFallback.class)
public interface ProductServiceClient {

    @Retry(name = "getProductById", fallbackMethod = "getProductByIdFallback")
    @RateLimiter(name = "getProductById", fallbackMethod = "getProductByIdFallback")
    @GetMapping(value = "/products/{productId}", consumes = "application/json")
    ProductDto getProductById(@PathVariable("productId") Long productId);



    @Retry(name = "getProductDetailsById", fallbackMethod = "getProductDetailsByIdFallback")
    @RateLimiter(name = "getProductDetailsById", fallbackMethod = "getProductDetailsByIdFallback")
    @GetMapping(value = "/products/{productId}/details", consumes = "application/json")
    ProductDetailsDto getProductDetailsById(@PathVariable("productId") Long productId);

}

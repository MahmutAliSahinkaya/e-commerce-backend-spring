package com.ecommerce.favouriteservice.service.client;

import com.ecommerce.favouriteservice.dto.ProductDetailsDto;
import com.ecommerce.favouriteservice.dto.ProductDto;
import com.ecommerce.favouriteservice.service.fallback.ProductFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Primary
@FeignClient(name = "product-service", url = "http://localhost:8081", fallback = ProductFallback.class)
public interface ProductServiceClient {

    @GetMapping(value = "/products/{productId}", consumes = "application/json")
    ProductDto getProductById(@PathVariable("productId") Long productId);

    @GetMapping(value = "/products/{productId}/details", consumes = "application/json")
    ProductDetailsDto getProductDetailsById(@PathVariable("productId") Long productId);

}

package com.ecommerce.favouriteservice.service.fallback;

import com.ecommerce.favouriteservice.dto.ProductDetailsDto;
import com.ecommerce.favouriteservice.dto.ProductDto;
import com.ecommerce.favouriteservice.service.client.ProductServiceClient;
import org.springframework.stereotype.Component;

@Component
public class ProductFallback implements ProductServiceClient {
    @Override
    public ProductDto getProductById(Long productId) {
        return getProductByIdFallback(new Exception("Product Service is down"));
    }

    @Override
    public ProductDetailsDto getProductDetailsById(Long productId) {
        return getProductDetailsByIdFallback(new Exception("Product Service is down"));
    }

    private ProductDto getProductByIdFallback(Throwable throwable) {
        System.out.println(throwable.getMessage());
        return null;
    }

    private ProductDetailsDto getProductDetailsByIdFallback(Throwable throwable) {
        System.out.println(throwable.getMessage());
        return null;
    }

}

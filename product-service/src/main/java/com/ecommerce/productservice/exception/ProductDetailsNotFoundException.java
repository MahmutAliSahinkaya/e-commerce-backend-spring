package com.ecommerce.productservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductDetailsNotFoundException extends RuntimeException {
    public ProductDetailsNotFoundException(String message) {
        super(message);
    }
}

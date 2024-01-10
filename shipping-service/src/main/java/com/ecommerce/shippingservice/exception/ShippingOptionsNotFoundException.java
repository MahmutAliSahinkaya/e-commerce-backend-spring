package com.ecommerce.shippingservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ShippingOptionsNotFoundException extends RuntimeException {
    public ShippingOptionsNotFoundException(String message) {
        super(message);
    }
}

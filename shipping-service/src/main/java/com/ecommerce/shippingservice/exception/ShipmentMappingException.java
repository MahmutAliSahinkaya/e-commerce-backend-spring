package com.ecommerce.shippingservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ShipmentMappingException extends RuntimeException {
    public ShipmentMappingException(String message) {
        super(message);
    }
}

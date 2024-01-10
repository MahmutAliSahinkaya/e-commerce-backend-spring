package com.ecommerce.favouriteservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FavouriteNotFoundException extends RuntimeException {

    public FavouriteNotFoundException(String message) {
        super(message);
    }
}

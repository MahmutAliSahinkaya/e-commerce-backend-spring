package com.ecommerce.apigateway.exception;


import org.springframework.security.core.AuthenticationException;

public class JwtTokenMalformedException extends AuthenticationException {

    private static final long serialVersionUID = 1L;

    public JwtTokenMalformedException(String message) {
        super(message);
    }
}

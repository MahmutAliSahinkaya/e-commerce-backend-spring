package com.ecommerce.paymentservice.service.fallback;

import com.ecommerce.paymentservice.dto.UserDto;
import com.ecommerce.paymentservice.service.client.UserServiceClient;
import org.springframework.stereotype.Component;

@Component
public class UserFallback implements UserServiceClient {

    @Override
    public UserDto getUserById(Long userId) {
        return getUserByIdFallback(new Exception("User Service is down"));
    }

    private UserDto getUserByIdFallback(Throwable throwable) {
        System.out.println(throwable.getMessage());
        return null;
    }
}

package com.ecommerce.favouriteservice.service.fallback;

import com.ecommerce.favouriteservice.dto.UserDto;
import com.ecommerce.favouriteservice.service.client.UserServiceClient;
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

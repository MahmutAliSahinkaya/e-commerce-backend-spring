package com.ecommerce.paymentservice.service.client;

import com.ecommerce.paymentservice.dto.UserDto;
import com.ecommerce.paymentservice.service.fallback.UserFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Primary
@FeignClient(name="user-service", url = "http://localhost:8080", fallback = UserFallback.class)
public interface UserServiceClient {


    @GetMapping(value = "/users/{userId}", consumes = "application/json")
    UserDto getUserById(@PathVariable("userId") Long userId);

}

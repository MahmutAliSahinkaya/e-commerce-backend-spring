package com.ecommerce.paymentservice.service.client;

import com.ecommerce.paymentservice.dto.UserDto;
import com.ecommerce.paymentservice.service.fallback.UserFallback;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Primary
@FeignClient(name="user-service", url = "http://localhost:8080", fallback = UserFallback.class)
public interface UserServiceClient {


    @Retry(name = "getUserById", fallbackMethod = "getUserByIdFallback")
    @RateLimiter(name = "getUserById", fallbackMethod = "getUserByIdFallback")
    @GetMapping(value = "/users/{userId}", consumes = "application/json")
    UserDto getUserById(@PathVariable("userId") Long userId);

}

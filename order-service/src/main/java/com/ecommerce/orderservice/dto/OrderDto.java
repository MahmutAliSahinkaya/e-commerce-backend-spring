package com.ecommerce.orderservice.dto;

import com.ecommerce.orderservice.entity.enums.OrderStatus;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Builder
public record OrderDto(
        Long orderId,
        @DateTimeFormat(pattern = "dd-MM-yyyy__HH:mm:ss:SSSSSS")
        LocalDateTime orderDate,
        String orderDescription,
        Double totalAmount,
        OrderStatus orderStatus,
        CartDto cartDto
) {
}

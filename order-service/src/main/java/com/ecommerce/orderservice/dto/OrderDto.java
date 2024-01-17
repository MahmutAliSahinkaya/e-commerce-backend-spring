package com.ecommerce.orderservice.dto;

import com.ecommerce.orderservice.entity.enums.OrderStatus;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record OrderDto(
        Long orderId,
        LocalDateTime orderDate,
        String orderDescription,
        Double totalAmount,
        OrderStatus orderStatus,
        CartDto cartDto
) {
}

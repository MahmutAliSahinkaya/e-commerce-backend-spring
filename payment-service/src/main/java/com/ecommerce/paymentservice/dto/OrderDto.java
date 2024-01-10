package com.ecommerce.paymentservice.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record OrderDto (
        Long orderId,
        LocalDateTime orderDate,
        String orderDescription,
        Double totalAmount
) {}

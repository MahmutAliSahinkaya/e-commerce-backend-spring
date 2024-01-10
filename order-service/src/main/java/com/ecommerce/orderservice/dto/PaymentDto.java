package com.ecommerce.orderservice.dto;

import com.ecommerce.orderservice.entity.enums.PaymentStatus;
import lombok.Builder;

@Builder
public record PaymentDto(
        Long paymentId,
        Boolean isPayed,
        PaymentStatus paymentStatus,
        OrderDto orderDto
){}

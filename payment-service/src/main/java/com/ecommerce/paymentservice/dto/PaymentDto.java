package com.ecommerce.paymentservice.dto;

import com.ecommerce.paymentservice.entity.enums.PaymentStatus;
import lombok.Builder;

@Builder
public record PaymentDto (
        Long paymentId,
        Long userId,
        Boolean isPayed,
        PaymentStatus paymentStatus,
        OrderDto orderDto
){}

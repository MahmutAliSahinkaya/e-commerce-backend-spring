package com.ecommerce.paymentservice.dto;

public record PaymentMessageDto(
        Long paymentId,
        Boolean isPayed,
        String userEmail,
        String userPhone
) {
}

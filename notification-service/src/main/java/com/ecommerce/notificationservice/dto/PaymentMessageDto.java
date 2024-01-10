package com.ecommerce.notificationservice.dto;

public record PaymentMessageDto(
        Long paymentId,
        Boolean isPayed,
        String userEmail,
        String userPhone
) {
}

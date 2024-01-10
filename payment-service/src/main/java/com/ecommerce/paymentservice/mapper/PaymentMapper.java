package com.ecommerce.paymentservice.mapper;

import com.ecommerce.paymentservice.dto.OrderDto;
import com.ecommerce.paymentservice.dto.PaymentDto;
import com.ecommerce.paymentservice.entity.Payment;

public class PaymentMapper {

    public static PaymentDto toDto(Payment payment) {
        return PaymentDto.builder()
                .paymentId(payment.getPaymentId())
                .isPayed(payment.getIsPayed())
                .paymentStatus(payment.getPaymentStatus())
                .orderDto(
                        OrderDto.builder()
                                .orderId(payment.getOrderId())
                                .build())
                .build();
    }

    public static Payment toEntity(PaymentDto paymentDto) {
        return Payment.builder()
                .paymentId(paymentDto.paymentId())
                .orderId(paymentDto.orderDto().orderId())
                .isPayed(paymentDto.isPayed())
                .paymentStatus(paymentDto.paymentStatus())
                .build();
    }
}

package com.ecommerce.paymentservice.service;

import com.ecommerce.paymentservice.dto.PaymentDto;

public interface PaymentService {
    void processPayment(PaymentDto paymentDto);

    boolean validatePayment(Long paymentId);

    PaymentDto getPaymentByUserId(Long userId);

    boolean updateCommunicationStatus(Long paymentId);

}

package com.ecommerce.orderservice.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PaymentStatus {
    NOT_STARTED("not_started"),
    IN_PROGRESS("in_progress"),
    REFUNDED("refunded"),
    COMPLETED("completed");

    private final String status;
}

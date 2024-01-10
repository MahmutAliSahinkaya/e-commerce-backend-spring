package com.ecommerce.shippingservice.dto;

import lombok.Builder;

@Builder
public record ShipmentDetailsDto (
        String recipientName,
        String recipientAddress,
        String recipientPhone,
        Double shippingCost

) {
}

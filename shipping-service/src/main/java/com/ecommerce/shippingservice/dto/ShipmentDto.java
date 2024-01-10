package com.ecommerce.shippingservice.dto;

import com.ecommerce.shippingservice.entity.enums.ShipmentStatus;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ShipmentDto (
        Long shipmentId,
        Long orderId,
        String trackingNumber,
        ShipmentStatus status,
        LocalDateTime shippedDate,
        LocalDateTime estimatedDeliveryDate

) {
}

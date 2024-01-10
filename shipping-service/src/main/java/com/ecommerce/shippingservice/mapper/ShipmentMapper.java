package com.ecommerce.shippingservice.mapper;

import com.ecommerce.shippingservice.dto.ShipmentDto;
import com.ecommerce.shippingservice.entity.Shipment;

public class ShipmentMapper {

    public static ShipmentDto toShipmentDTO(Shipment shipment) {
        return ShipmentDto.builder()
                .shipmentId(shipment.getShipmentId())
                .orderId(shipment.getOrderId())
                .trackingNumber(shipment.getTrackingNumber())
                .status(shipment.getShipmentStatus())
                .shippedDate(shipment.getShippedDate())
                .estimatedDeliveryDate(shipment.getEstimatedDeliveryDate())
                .build();
    }

    public static Shipment toShipmentEntity(ShipmentDto shipmentDto) {
        Shipment shipment = new Shipment();
        shipment.setShipmentId(shipmentDto.shipmentId());
        shipment.setOrderId(shipmentDto.orderId());
        shipment.setTrackingNumber(shipmentDto.trackingNumber());
        shipment.setShipmentStatus(shipmentDto.status());
        shipment.setShippedDate(shipmentDto.shippedDate());
        shipment.setEstimatedDeliveryDate(shipmentDto.estimatedDeliveryDate());
        return shipment;
    }
}

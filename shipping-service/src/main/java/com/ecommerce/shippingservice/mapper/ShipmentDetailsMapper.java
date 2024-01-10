package com.ecommerce.shippingservice.mapper;

import com.ecommerce.shippingservice.dto.ShipmentDetailsDto;
import com.ecommerce.shippingservice.entity.ShipmentDetails;

public class ShipmentDetailsMapper {

    public static ShipmentDetailsDto toShipmentDetailsDto(ShipmentDetails details) {
        return ShipmentDetailsDto.builder()
                .recipientName(details.getRecipientName())
                .recipientAddress(details.getRecipientAddress())
                .recipientPhone(details.getRecipientPhone())
                .shippingCost(details.getShippingCost())
                .build();
    }

    public static ShipmentDetails toShipmentDetailsEntity(ShipmentDetailsDto shipmentDetailsDto) {
        ShipmentDetails shipmentDetails = new ShipmentDetails();
        shipmentDetails.setRecipientName(shipmentDetailsDto.recipientName());
        shipmentDetails.setRecipientAddress(shipmentDetailsDto.recipientAddress());
        shipmentDetails.setRecipientPhone(shipmentDetailsDto.recipientPhone());
        shipmentDetails.setShippingCost(shipmentDetailsDto.shippingCost());
        return shipmentDetails;
    }

}

package com.ecommerce.shippingservice.service;

import com.ecommerce.shippingservice.dto.ShipmentDto;
import com.ecommerce.shippingservice.entity.Shipment;

public interface ShippingService {

    Shipment createShipment(ShipmentDto shipmentDto);

    ShipmentDto trackShipment(Long shipmentId);

    void updateShipment(Long shipmentId, ShipmentDto shipmentDto);

}

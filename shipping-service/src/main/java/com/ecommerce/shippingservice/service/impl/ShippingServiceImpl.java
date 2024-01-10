package com.ecommerce.shippingservice.service.impl;

import com.ecommerce.shippingservice.dto.ShipmentDto;
import com.ecommerce.shippingservice.entity.Shipment;
import com.ecommerce.shippingservice.exception.ShipmentNotFoundException;
import com.ecommerce.shippingservice.mapper.ShipmentMapper;
import com.ecommerce.shippingservice.repository.ShipmentRepository;
import com.ecommerce.shippingservice.service.ShippingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ShippingServiceImpl implements ShippingService {

    private static final Logger log = LoggerFactory.getLogger(ShippingServiceImpl.class);

    private final ShipmentRepository shipmentRepository;

    public ShippingServiceImpl(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }


    @Override
    public Shipment createShipment(ShipmentDto shipmentDto) {
        log.info("Entering createShipment with ShipmentDto: {}", shipmentDto);
        if (shipmentDto == null) {
            throw new ShipmentNotFoundException("ShipmentDto cannot be null");
        }
        Shipment shipment = ShipmentMapper.toShipmentEntity(shipmentDto);
        Shipment result = shipmentRepository.save(shipment);
        log.info("Exiting createShipment with result: {}", result);
        return result;
    }


    @Override
    public void updateShipment(Long shipmentId, ShipmentDto shipmentDto) {
        log.info("Entering updateShipment with shipmentId: {}, ShipmentDto: {}", shipmentId, shipmentDto);
        Shipment shipment = shipmentRepository.findById(shipmentId)
                .orElseThrow(() -> new ShipmentNotFoundException("Shipment with id " + shipmentId + " not found"));
        shipment.setOrderId(shipmentDto.orderId());
        shipment.setTrackingNumber(shipmentDto.trackingNumber());
        shipment.setShipmentStatus(shipmentDto.status());
        shipment.setShippedDate(shipmentDto.shippedDate());
        shipment.setEstimatedDeliveryDate(shipmentDto.estimatedDeliveryDate());
        shipmentRepository.save(shipment);
        log.info("Exiting updateShipment");
    }


    @Override
    public ShipmentDto trackShipment(Long shipmentId) {
        log.info("Entering trackShipment with shipmentId: {}", shipmentId);
        Shipment shipment = shipmentRepository.findById(shipmentId)
                .orElseThrow(() -> new ShipmentNotFoundException("Shipment with id " + shipmentId + " not found"));
        ShipmentDto result = ShipmentMapper.toShipmentDTO(shipment);
        log.info("Exiting trackShipment with result: {}", result);
        return result;
    }
}

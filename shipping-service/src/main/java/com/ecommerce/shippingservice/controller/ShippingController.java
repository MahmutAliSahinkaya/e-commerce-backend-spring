package com.ecommerce.shippingservice.controller;

import com.ecommerce.shippingservice.dto.ShipmentDto;
import com.ecommerce.shippingservice.entity.Shipment;
import com.ecommerce.shippingservice.service.ShippingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shippings")
public class ShippingController {

    private final ShippingService shippingService;

    public ShippingController(ShippingService shippingService) {
        this.shippingService = shippingService;
    }

    @PostMapping("/create")
    public ResponseEntity<Shipment> createShipment(@RequestBody ShipmentDto shipmentDto) {
        Shipment shipment = shippingService.createShipment(shipmentDto);
        return new ResponseEntity<>(shipment, HttpStatus.CREATED);
    }

    @GetMapping("/track/{shipmentId}")
    public ResponseEntity<ShipmentDto> trackShipment(@PathVariable Long shipmentId) {
        ShipmentDto shipmentDto = shippingService.trackShipment(shipmentId);
        return new ResponseEntity<>(shipmentDto, HttpStatus.OK);
    }

    @PutMapping("/update/{shipmentId}")
    public ResponseEntity<Void> updateShipment(@PathVariable Long shipmentId,
                                               @RequestBody ShipmentDto shipmentDto) {
        shippingService.updateShipment(shipmentId, shipmentDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

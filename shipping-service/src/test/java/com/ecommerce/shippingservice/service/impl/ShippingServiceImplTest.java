package com.ecommerce.shippingservice.service.impl;

import com.ecommerce.shippingservice.dto.ShipmentDto;
import com.ecommerce.shippingservice.entity.Shipment;
import com.ecommerce.shippingservice.entity.enums.ShipmentStatus;
import com.ecommerce.shippingservice.exception.ShipmentNotFoundException;
import com.ecommerce.shippingservice.repository.ShipmentRepository;
import com.ecommerce.shippingservice.repository.ShippingOptionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShippingServiceImplTest {

    @Mock
    private ShipmentRepository shipmentRepository;

    @Mock
    private ShippingOptionRepository shippingOptionRepository;

    @InjectMocks
    private ShippingServiceImpl shippingService;

    private ShipmentDto shipmentDto;
    private Shipment shipment;

    @BeforeEach
    void setUp() {
        shipmentDto = getMockShipmentDto();
        shipment = getMockShipment();
    }

    @Test
    @DisplayName("Create Shipment - Success")
    void testCreateShipmentSuccess() {
        when(shipmentRepository.save(any(Shipment.class))).thenReturn(shipment);

        Shipment result = shippingService.createShipment(shipmentDto);

        assertNotNull(result);
        assertEquals(shipment.getShipmentId(), result.getShipmentId());
    }

    @Test
    @DisplayName("Create Shipment - Failure")
    void testCreateShipmentFailure() {
        assertThrows(ShipmentNotFoundException.class, () -> shippingService.createShipment(null));
    }

    @Test
    @DisplayName("Update Shipment - Success")
    void testUpdateShipmentSuccess() {
        when(shipmentRepository.findById(anyLong())).thenReturn(Optional.of(shipment));

        assertDoesNotThrow(() -> shippingService.updateShipment(1L, shipmentDto));
    }

    @Test
    @DisplayName("Update Shipment - Failure")
    void testUpdateShipmentFailure() {
        when(shipmentRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ShipmentNotFoundException.class, () -> shippingService.updateShipment(1L, shipmentDto));
    }

    @Test
    @DisplayName("Track Shipment - Success")
    void testTrackShipmentSuccess() {
        when(shipmentRepository.findById(anyLong())).thenReturn(Optional.of(shipment));

        ShipmentDto result = shippingService.trackShipment(1L);

        assertNotNull(result);
        assertEquals(shipment.getShipmentId(), result.shipmentId());
    }

    @Test
    @DisplayName("Track Shipment - Failure")
    void testTrackShipmentFailure() {
        when(shipmentRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ShipmentNotFoundException.class, () -> shippingService.trackShipment(1L));
    }

    private ShipmentDto getMockShipmentDto() {
        return ShipmentDto.builder()
                .shipmentId(1L)
                .orderId(1L)
                .trackingNumber("1234567890")
                .status(ShipmentStatus.PENDING)
                .shippedDate(LocalDateTime.now())
                .estimatedDeliveryDate(LocalDateTime.now().plusDays(5))
                .build();
    }

    private Shipment getMockShipment() {
        return Shipment.builder()
                .shipmentId(1L)
                .orderId(1L)
                .trackingNumber("1234567890")
                .shipmentStatus(ShipmentStatus.PENDING)
                .shippedDate(LocalDateTime.now())
                .estimatedDeliveryDate(LocalDateTime.now().plusDays(5))
                .build();
    }
}
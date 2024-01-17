package com.ecommerce.shippingservice.controller;

import com.ecommerce.shippingservice.dto.ShipmentDto;
import com.ecommerce.shippingservice.entity.Shipment;
import com.ecommerce.shippingservice.entity.enums.ShipmentStatus;
import com.ecommerce.shippingservice.service.impl.ShippingServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ShippingController.class)
@ContextConfiguration(classes = {ShippingController.class})
class ShippingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShippingServiceImpl shippingService;

    @Autowired
    private ObjectMapper objectMapper;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Create Shipment - Should Return Created Shipment")
    void createShipment_ShouldReturnCreatedShipment() throws Exception {
        ShipmentDto shipmentDto = getMockShipmentDto();
        Shipment shipment = getMockShipment();
        when(shippingService.createShipment(any(ShipmentDto.class))).thenReturn(shipment);

        mockMvc.perform(MockMvcRequestBuilders.post("/shippings/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(shipmentDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.shipmentId").value(shipment.getShipmentId()))
                .andExpect(jsonPath("$.trackingNumber").value(shipment.getTrackingNumber()));
    }

    @Test
    @DisplayName("Track Shipment - Should Return Shipment")
    void trackShipment_ShouldReturnShipment() throws Exception {
        ShipmentDto shipmentDto = getMockShipmentDto();
        when(shippingService.trackShipment(anyLong())).thenReturn(shipmentDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/shippings/track/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shipmentId").value(shipmentDto.shipmentId()))
                .andExpect(jsonPath("$.trackingNumber").value(shipmentDto.trackingNumber()));
    }

    @Test
    @DisplayName("Update Shipment - Should Return No Content")
    void updateShipment_ShouldReturnNoContent() throws Exception {
        ShipmentDto shipmentDto = getMockShipmentDto();

        mockMvc.perform(MockMvcRequestBuilders.put("/shippings/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(shipmentDto)))
                .andExpect(status().isNoContent());
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
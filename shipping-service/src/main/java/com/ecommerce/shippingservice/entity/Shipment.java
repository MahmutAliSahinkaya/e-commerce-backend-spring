package com.ecommerce.shippingservice.entity;

import com.ecommerce.shippingservice.entity.enums.ShipmentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "shipment")
public class Shipment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shipmentId;
    private Long orderId;
    private String trackingNumber;

    @Enumerated(EnumType.STRING)
    private ShipmentStatus shipmentStatus;

    private String trackingInfo;

    private LocalDateTime shippedDate;
    private LocalDateTime estimatedDeliveryDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shipment_details_id", referencedColumnName = "shipmentDetailsId")
    private ShipmentDetails shipmentDetails;

}

package com.ecommerce.shippingservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "shipment_details")
public class ShipmentDetails extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shipmentDetailsId;

    private String recipientName;
    private String recipientAddress;
    private String recipientPhone;
    private Double shippingCost;
    private Long weight;

    @OneToOne(mappedBy = "shipmentDetails")
    private Shipment shipment;

}

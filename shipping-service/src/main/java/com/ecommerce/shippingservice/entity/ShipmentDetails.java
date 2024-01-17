package com.ecommerce.shippingservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotBlank(message = "Recipient Name is required")
    private String recipientName;

    @NotBlank(message = "Recipient Address is required")
    private String recipientAddress;

    @NotBlank(message = "Recipient Phone Number is required")
    private String recipientPhone;

    @NotNull(message = "Shipping Cost is required")
    private Double shippingCost;

    @NotNull(message = "Shipping Weight is required")
    private Long weight;

    @OneToOne(mappedBy = "shipmentDetails")
    private Shipment shipment;

}

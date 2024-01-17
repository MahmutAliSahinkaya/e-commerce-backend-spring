package com.ecommerce.shippingservice.entity;

import com.ecommerce.shippingservice.entity.enums.ShippingSpeed;
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
@Table(name = "shipping_option")
public class ShippingOption extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shippingOptionId;

    @NotBlank(message = "Carrier is required")
    private String carrier;

    @NotBlank(message = "Service Type is required")
    private String serviceType;

    @NotNull(message = "Cost is required")
    private Double cost;

    @Enumerated(EnumType.STRING)
    private ShippingSpeed deliverySpeed;

    private String description;
}

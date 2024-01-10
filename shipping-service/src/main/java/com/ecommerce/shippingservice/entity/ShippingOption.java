package com.ecommerce.shippingservice.entity;

import com.ecommerce.shippingservice.entity.enums.ShippingSpeed;
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
@Table(name = "shipping_option")
public class ShippingOption extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shippingOptionId;

    private String carrier;
    private String serviceType;
    private Double cost;

    @Enumerated(EnumType.STRING)
    private ShippingSpeed deliverySpeed;

    private String description;
}

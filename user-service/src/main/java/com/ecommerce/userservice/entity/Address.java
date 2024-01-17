package com.ecommerce.userservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "address")
public class Address extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id", unique = true, nullable = false)
    private Long addressId;

    @NotBlank(message = "Address is required")
    @Size(min = 5, max = 200)
    @Column(name = "full_address", unique = true)
    private String fullAddress;

    @NotBlank(message = "Postal Code is required")
    @Size(min = 5, max = 50)
    @Column(name = "postal_code")
    private String postalCode;

    @NotBlank(message = "Country is required")
    @Size(min = 2, max = 50)
    private String city;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}

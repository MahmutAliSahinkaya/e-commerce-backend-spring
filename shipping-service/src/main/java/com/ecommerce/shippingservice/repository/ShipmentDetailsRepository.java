package com.ecommerce.shippingservice.repository;

import com.ecommerce.shippingservice.entity.ShipmentDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentDetailsRepository extends JpaRepository<ShipmentDetails, Long> {
}

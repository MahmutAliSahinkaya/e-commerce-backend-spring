package com.ecommerce.shippingservice.repository;

import com.ecommerce.shippingservice.entity.ShippingOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingOptionRepository extends JpaRepository<ShippingOption, Long> {
}

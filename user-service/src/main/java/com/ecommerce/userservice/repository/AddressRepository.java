package com.ecommerce.userservice.repository;

import com.ecommerce.userservice.entity.Address;
import com.ecommerce.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<Address> findByAddressIdAndUser(Long addressId, User user);

}
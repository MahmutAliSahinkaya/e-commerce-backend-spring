package com.ecommerce.userservice.service;

import com.ecommerce.userservice.dto.AddressDto;

public interface AddressService {
    AddressDto addAddress(Long userId, AddressDto addressDto);

    AddressDto updateAddress(Long userId, Long addressId, AddressDto addressDto);

    AddressDto getAddress(Long userId, Long addressId);

    void deleteAddress(Long userId, Long addressId);
}

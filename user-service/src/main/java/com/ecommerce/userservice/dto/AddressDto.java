package com.ecommerce.userservice.dto;

import lombok.Builder;

@Builder
public record AddressDto(
        Long addressId,
        String fullAddress,
        String postalCode,
        String city,
        UserDto userDto
) {
}

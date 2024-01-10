package com.ecommerce.userservice.mapper;

import com.ecommerce.userservice.dto.AddressDto;
import com.ecommerce.userservice.dto.UserDto;
import com.ecommerce.userservice.entity.Address;
import com.ecommerce.userservice.entity.User;

public class AddressMapper {
    public static AddressDto toDto(Address address) {
        return AddressDto.builder()
                .addressId(address.getAddressId())
                .fullAddress(address.getFullAddress())
                .postalCode(address.getPostalCode())
                .city(address.getCity())
                .userDto(
                        UserDto.builder()
                                .userId(address.getUser().getUserId())
                                .firstName(address.getUser().getFirstName())
                                .lastName(address.getUser().getLastName())
                                .username(address.getUser().getUsername())
                                .imageUrl(address.getUser().getImageUrl())
                                .email(address.getUser().getEmail())
                                .phone(address.getUser().getPhone())
                                .build())
                .build();
    }

    public static Address toEntity(AddressDto addressDto) {
        User user = new User();
        user.setUserId(addressDto.userDto().userId());
        user.setFirstName(addressDto.userDto().firstName());
        user.setLastName(addressDto.userDto().lastName());
        user.setUsername(addressDto.userDto().username());
        user.setImageUrl(addressDto.userDto().imageUrl());
        user.setEmail(addressDto.userDto().email());
        user.setPhone(addressDto.userDto().phone());

        return Address.builder()
                .addressId(addressDto.addressId())
                .fullAddress(addressDto.fullAddress())
                .postalCode(addressDto.postalCode())
                .city(addressDto.city())
                .user(user)
                .build();
    }

}

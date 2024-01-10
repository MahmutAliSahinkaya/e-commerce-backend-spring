package com.ecommerce.userservice.service.impl;

import com.ecommerce.userservice.dto.AddressDto;
import com.ecommerce.userservice.entity.Address;
import com.ecommerce.userservice.entity.User;
import com.ecommerce.userservice.exception.AddressNotFoundException;
import com.ecommerce.userservice.exception.UserNotFoundException;
import com.ecommerce.userservice.mapper.AddressMapper;
import com.ecommerce.userservice.repository.AddressRepository;
import com.ecommerce.userservice.repository.UserRepository;
import com.ecommerce.userservice.service.AddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {

    private static final Logger log = LoggerFactory.getLogger(AddressService.class);

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public AddressServiceImpl(AddressRepository addressRepository,
                              UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    public AddressDto addAddress(Long userId, AddressDto addressDto) {
        log.info("Adding address for user with id: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        Address address = AddressMapper.toEntity(addressDto);
        address.setUser(user);
        address.setFullAddress(address.getFullAddress());
        address.setPostalCode(address.getPostalCode());
        address.setCity(address.getCity());

        Address savedAddress = addressRepository.save(address);
        log.info("Address added for user with id: {}", userId);

        return AddressMapper.toDto(savedAddress);
    }


    public AddressDto updateAddress(Long userId, Long addressId, AddressDto addressDto) {
        log.info("Updating address with id: {} for user with id: {}", addressId, userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id : " + userId));

        Address address = AddressMapper.toEntity(addressDto);
        address.setUser(user);
        address.setFullAddress(address.getFullAddress());
        address.setPostalCode(address.getPostalCode());
        address.setCity(address.getCity());

        Address savedAddress = addressRepository.save(address);
        log.info("Address with id: {} updated for user with id: {}", addressId, userId);

        return AddressMapper.toDto(savedAddress);
    }


    public AddressDto getAddress(Long userId, Long addressId) {
        log.info("Retrieving address with id: {} for user with id: {}", addressId, userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new AddressNotFoundException("Address not found with id: " + addressId));
        log.info("Address with id: {} retrieved for user with id: {}", addressId, userId);

        return AddressMapper.toDto(address);
    }


    public void deleteAddress(Long userId, Long addressId) {
        log.info("Deleting address with id: {} for user with id: {}", addressId, userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        Address address = addressRepository.findByAddressIdAndUser(addressId, user)
                .orElseThrow(() -> new AddressNotFoundException("Address not found with id: " + addressId + " for user with id: " + userId));

        addressRepository.delete(address);
        log.info("Address with id: {} deleted for user with id: {}", addressId, userId);
    }
}

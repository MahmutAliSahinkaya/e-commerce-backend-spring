package com.ecommerce.userservice.controller;

import com.ecommerce.userservice.dto.AddressDto;
import com.ecommerce.userservice.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/{userId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<AddressDto> addAddressToUser(@RequestBody AddressDto addressDto,
                                                       @PathVariable Long userId) {
        AddressDto savedAddress = addressService.addAddress(userId, addressDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAddress);
    }

    @PutMapping("/{userId}/{addressId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<AddressDto> updateAddressDetails(@PathVariable Long userId,
                                                           @PathVariable Long addressId,
                                                           @RequestBody AddressDto updatedAddressDto) {
        AddressDto updated = addressService.updateAddress(userId, addressId, updatedAddressDto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{userId}/{addressId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<AddressDto> getAddressDetailsById(@PathVariable Long userId,
                                                            @PathVariable Long addressId) {
        AddressDto address = addressService.getAddress(userId, addressId);
        return ResponseEntity.ok(address);
    }

    @DeleteMapping("/{userId}/{addressId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAddressById(@PathVariable Long userId,
                                                  @PathVariable Long addressId) {
        addressService.deleteAddress(userId, addressId);
        return ResponseEntity.noContent().build();
    }

}

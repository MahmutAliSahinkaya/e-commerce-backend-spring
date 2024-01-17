package com.ecommerce.userservice.service.impl;

import com.ecommerce.userservice.dto.AddressDto;
import com.ecommerce.userservice.dto.UserDto;
import com.ecommerce.userservice.entity.Address;
import com.ecommerce.userservice.entity.User;
import com.ecommerce.userservice.exception.AddressNotFoundException;
import com.ecommerce.userservice.exception.UserNotFoundException;
import com.ecommerce.userservice.repository.AddressRepository;
import com.ecommerce.userservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressServiceImplTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AddressServiceImpl addressService;

    private Address address;
    private AddressDto addressDto;
    private User user;
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        address = getMockAddress();
        addressDto = getMockAddressDto();
        user = getMockUser();
        userDto = getMockUserDto();
    }

    @Test
    @DisplayName("Add Address Success")
    void shouldAddAddressSuccessfully() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(addressRepository.save(any(Address.class))).thenReturn(address);

        AddressDto result = addressService.addAddress(user.getUserId(), addressDto);

        assertNotNull(result);
        assertEquals(addressDto.addressId(), result.addressId());
        assertEquals(addressDto.fullAddress(), result.fullAddress());
        assertEquals(addressDto.postalCode(), result.postalCode());
        assertEquals(addressDto.city(), result.city());
        assertEquals(addressDto.userDto().userId(), result.userDto().userId());
        assertEquals(addressDto.userDto().firstName(), result.userDto().firstName());
        assertEquals(addressDto.userDto().lastName(), result.userDto().lastName());
        assertEquals(addressDto.userDto().username(), result.userDto().username());
        assertEquals(addressDto.userDto().imageUrl(), result.userDto().imageUrl());
        assertEquals(addressDto.userDto().email(), result.userDto().email());
        assertEquals(addressDto.userDto().phone(), result.userDto().phone());
    }

    @Test
    @DisplayName("Add Address Failure")
    void shouldThrowExceptionWhenUserNotFoundWhileAddingAddress() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> addressService.addAddress(user.getUserId(), addressDto));
    }


    @Test
    @DisplayName("Update Address Success")
    void shouldUpdateAddressSuccessfully() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(addressRepository.save(any(Address.class))).thenReturn(address);

        AddressDto result = addressService.updateAddress(user.getUserId(), address.getAddressId(), addressDto);

        assertNotNull(result);
        assertEquals(addressDto.addressId(), result.addressId());
        assertEquals(addressDto.fullAddress(), result.fullAddress());
        assertEquals(addressDto.postalCode(), result.postalCode());
        assertEquals(addressDto.city(), result.city());
        assertEquals(addressDto.userDto().userId(), result.userDto().userId());
        assertEquals(addressDto.userDto().firstName(), result.userDto().firstName());
        assertEquals(addressDto.userDto().lastName(), result.userDto().lastName());
        assertEquals(addressDto.userDto().username(), result.userDto().username());
        assertEquals(addressDto.userDto().imageUrl(), result.userDto().imageUrl());
        assertEquals(addressDto.userDto().email(), result.userDto().email());
        assertEquals(addressDto.userDto().phone(), result.userDto().phone());
    }

    @Test
    @DisplayName("Update Address Failure")
    void shouldThrowExceptionWhenUserNotFoundWhileUpdatingAddress() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> addressService.updateAddress(user.getUserId(), address.getAddressId(), addressDto));
    }

    @Test
    @DisplayName("Get Address Success")
    void shouldGetAddressSuccessfully() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(addressRepository.findById(anyLong())).thenReturn(Optional.of(address));

        AddressDto result = addressService.getAddress(user.getUserId(), address.getAddressId());

        assertNotNull(result);
        assertEquals(addressDto.addressId(), result.addressId());
        assertEquals(addressDto.fullAddress(), result.fullAddress());
        assertEquals(addressDto.postalCode(), result.postalCode());
        assertEquals(addressDto.city(), result.city());
        assertEquals(addressDto.userDto().userId(), result.userDto().userId());
        assertEquals(addressDto.userDto().firstName(), result.userDto().firstName());
        assertEquals(addressDto.userDto().lastName(), result.userDto().lastName());
        assertEquals(addressDto.userDto().username(), result.userDto().username());
        assertEquals(addressDto.userDto().imageUrl(), result.userDto().imageUrl());
        assertEquals(addressDto.userDto().email(), result.userDto().email());
        assertEquals(addressDto.userDto().phone(), result.userDto().phone());
    }

    @Test
    @DisplayName("Get Address Failure")
    void shouldThrowExceptionWhenAddressNotFoundWhileGettingAddress() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(addressRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(AddressNotFoundException.class, () -> addressService.getAddress(user.getUserId(), address.getAddressId()));
    }

    @Test
    @DisplayName("Delete Address Success")
    void shouldDeleteAddressSuccessfully() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(addressRepository.findByAddressIdAndUser(anyLong(), any(User.class))).thenReturn(Optional.of(address));

        addressService.deleteAddress(user.getUserId(), address.getAddressId());

        verify(addressRepository, times(1)).delete(address);
    }

    @Test
    @DisplayName("Delete Address Failure")
    void shouldThrowExceptionWhenUserNotFoundWhileDeletingAddress() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> addressService.deleteAddress(user.getUserId(), address.getAddressId()));
    }

    private Address getMockAddress() {
        return Address.builder()
                .addressId(1L)
                .fullAddress("123 Test Street")
                .postalCode("12345")
                .city("Test City")
                .user(getMockUser())
                .build();
    }

    private AddressDto getMockAddressDto() {
        return AddressDto.builder()
                .addressId(1L)
                .fullAddress("123 Test Street")
                .postalCode("12345")
                .city("Test City")
                .userDto(getMockUserDto())
                .build();
    }

    private User getMockUser() {
        return User.builder()
                .userId(1L)
                .firstName("testFirstName")
                .lastName("testLastName")
                .username("testUsername")
                .email("test@email.com")
                .imageUrl("testImageUrl")
                .phone("0123456789")
                .addresses(Set.of())
                .build();
    }

    private UserDto getMockUserDto() {
        return UserDto.builder()
                .userId(1L)
                .firstName("testFirstName")
                .lastName("testLastName")
                .username("testUsername")
                .imageUrl("testImageUrl")
                .email("test@email.com")
                .phone("0123456789")
                .addressDtos(Set.of())
                .build();
    }

}
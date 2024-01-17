package com.ecommerce.userservice.controller;

import com.ecommerce.userservice.dto.AddressDto;
import com.ecommerce.userservice.dto.UserDto;
import com.ecommerce.userservice.service.impl.AddressServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AddressController.class)
@ContextConfiguration(classes = {AddressController.class})
class AddressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AddressServiceImpl addressService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Add Address to User - Should Return Created")
    public void addAddressToUser_ShouldCreateAddress() throws Exception {
        AddressDto addressDto = getMockAddressDto();
        when(addressService.addAddress(anyLong(), any(AddressDto.class))).thenReturn(addressDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/addresses/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addressDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.addressId").value(1))
                .andExpect(jsonPath("$.fullAddress").value("123 Test Street"));
    }


    @Test
    @DisplayName("Update Address Details - Should Return Ok")
    public void updateAddressDetails_ShouldUpdateAddress() throws Exception {
        AddressDto updatedAddressDto = getMockAddressDto();
        when(addressService.updateAddress(anyLong(), anyLong(), any(AddressDto.class))).thenReturn(updatedAddressDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/addresses/1/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedAddressDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.addressId").value(1))
                .andExpect(jsonPath("$.fullAddress").value("123 Test Street"));
    }

    @Test
    @DisplayName("Get Address Details By Id - Should Return Address")
    public void getAddressDetailsById_ShouldReturnAddress() throws Exception {
        AddressDto addressDto = getMockAddressDto();
        when(addressService.getAddress(anyLong(), anyLong())).thenReturn(addressDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/addresses/1/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.addressId").value(1))
                .andExpect(jsonPath("$.fullAddress").value("123 Test Street"));
    }

    @Test
    @DisplayName("Delete Address By Id - Should Return No Content")
    public void deleteAddressById_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/addresses/1/1"))
                .andExpect(status().isNoContent());
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

    private UserDto getMockUserDto() {
        return UserDto.builder()
                .userId(1L)
                .firstName("testFirstName")
                .lastName("testLastName")
                .username("testUsername")
                .imageUrl("https://testimageurl.com")
                .email("test@email.com")
                .phone("0123456789")
                .addressDtos(Set.of())
                .build();
    }
}
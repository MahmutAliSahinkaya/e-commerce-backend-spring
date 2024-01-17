package com.ecommerce.orderservice.controller;

import com.ecommerce.orderservice.dto.CartItemDto;
import com.ecommerce.orderservice.service.impl.CartServiceImpl;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CartController.class)
@ContextConfiguration(classes = {CartController.class})
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartServiceImpl cartService;

    @Autowired
    private ObjectMapper objectMapper;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Add Item to Cart - Should Return Created")
    void addItemToCart_ShouldReturnCreated() throws Exception {
        CartItemDto cartItemDto = getMockCartItemDto();
        doNothing().when(cartService).addItemToCart(any(CartItemDto.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/carts/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartItemDto)))
                .andExpect(status().isCreated());
    }


    @Test
    @DisplayName("Update Cart Item - Should Return Updated Cart Item")
    void updateCartItem_ShouldReturnUpdatedCartItem() throws Exception {
        Long cartItemId = 1L;
        CartItemDto cartItemDto = getMockCartItemDto();
        when(cartService.updateCartItem(anyLong(), any(CartItemDto.class))).thenReturn(cartItemDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/carts/items/{cartItemId}", cartItemId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartItemDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cartItemId").value(cartItemDto.cartItemId()));
    }


    @Test
    @DisplayName("Remove Item From Cart - Should Return No Content")
    void removeItemFromCart_ShouldReturnNoContent() throws Exception {
        Long cartItemId = 1L;
        doNothing().when(cartService).removeItemFromCart(anyLong());

        mockMvc.perform(MockMvcRequestBuilders.delete("/carts/items/{cartItemId}", cartItemId))
                .andExpect(status().isNoContent());
    }

    private CartItemDto getMockCartItemDto() {
        return CartItemDto.builder()
                .cartItemId(1L)
                .productId(1L)
                .quantity(1L)
                .cartId(1L)
                .build();
    }
}
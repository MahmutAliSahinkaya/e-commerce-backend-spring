package com.ecommerce.orderservice.service.impl;

import com.ecommerce.orderservice.dto.*;
import com.ecommerce.orderservice.entity.CartItem;
import com.ecommerce.orderservice.entity.enums.OrderStatus;
import com.ecommerce.orderservice.entity.enums.PaymentStatus;
import com.ecommerce.orderservice.exception.CartItemNotFoundException;
import com.ecommerce.orderservice.mapper.CartItemMapper;
import com.ecommerce.orderservice.mapper.CartMapper;
import com.ecommerce.orderservice.repository.CartItemRepository;
import com.ecommerce.orderservice.repository.CartRepository;
import com.ecommerce.orderservice.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private CartServiceImpl cartService;

    private CartDto cartDto;
    private CartItemDto cartItemDto;
    private OrderDto orderDto;
    private PaymentDto paymentDto;
    private ProductDto productDto;
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        cartDto = getMockCartDto();
        cartItemDto = getMockCartItemDto();
        orderDto = getMockOrderDto();
        paymentDto = getMockPaymentDto();
        productDto = getMockProductDto();
        userDto = getMockUserDto();
    }

    @Test
    @DisplayName("Add Item to Cart - Success")
    void shouldAddItemToCartSuccessfully() {
        when(cartRepository.findById(anyLong())).thenReturn(Optional.of(CartMapper.mapToEntity(cartDto)));
        when(cartItemRepository.save(any(CartItem.class))).thenReturn(CartItemMapper.mapToEntity(cartItemDto));

        cartService.addItemToCart(cartItemDto);

        verify(cartItemRepository).save(any(CartItem.class));
    }

    @Test
    @DisplayName("Add Item to Cart - Failure")
    void shouldThrowCartItemNotFoundExceptionWhenCartDoesNotExist() {
        when(cartRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(CartItemNotFoundException.class, () -> cartService.addItemToCart(cartItemDto));
    }


    @Test
    @DisplayName("Add Item to Cart - Failure due to null CartItemDto")
    void shouldThrowCartItemNotFoundExceptionWhenCartItemDtoIsNull() {
        assertThrows(CartItemNotFoundException.class, () -> cartService.addItemToCart(null));
    }

    @Test
    @DisplayName("Update Cart Item - Success")
    void shouldUpdateCartItemSuccessfully() {
        when(cartItemRepository.findById(anyLong())).thenReturn(Optional.of(CartItemMapper.mapToEntity(cartItemDto)));
        when(cartRepository.findById(anyLong())).thenReturn(Optional.of(CartMapper.mapToEntity(cartDto)));
        when(cartItemRepository.save(any(CartItem.class))).thenReturn(CartItemMapper.mapToEntity(cartItemDto));

        CartItemDto result = cartService.updateCartItem(cartItemDto.cartItemId(), cartItemDto);

        assertNotNull(result);
        assertEquals(cartItemDto.cartItemId(), result.cartItemId());
    }

    @Test
    @DisplayName("Update Cart Item - Failure")
    void shouldThrowCartItemNotFoundExceptionWhenCartItemDoesNotExist() {
        when(cartItemRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(CartItemNotFoundException.class, () -> cartService.updateCartItem(cartItemDto.cartItemId(), cartItemDto));
    }


    @Test
    @DisplayName("Update Cart Item - Failure due to non-existent Cart")
    void shouldThrowCartItemNotFoundExceptionWhenCartDoesNotExist2() {
        when(cartItemRepository.findById(anyLong())).thenReturn(Optional.of(CartItemMapper.mapToEntity(cartItemDto)));
        when(cartRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(CartItemNotFoundException.class, () -> cartService.updateCartItem(cartItemDto.cartItemId(), cartItemDto));
    }

    @Test
    @DisplayName("Remove Item From Cart - Success")
    void shouldRemoveItemFromCartSuccessfully() {
        when(cartItemRepository.findById(anyLong())).thenReturn(Optional.of(CartItemMapper.mapToEntity(cartItemDto)));

        cartService.removeItemFromCart(cartItemDto.cartItemId());

        verify(cartItemRepository).delete(any(CartItem.class));
    }

    @Test
    @DisplayName("Remove Item From Cart - Failure")
    void shouldThrowCartItemNotFoundExceptionWhenRemovingNonExistentCartItem() {
        when(cartItemRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(CartItemNotFoundException.class, () -> cartService.removeItemFromCart(cartItemDto.cartItemId()));
    }

    private CartDto getMockCartDto() {
        return CartDto.builder()
                .cartId(1L)
                .userId(1L)
                .orderDtos(new HashSet<>())
                .build();
    }

    private CartItemDto getMockCartItemDto() {
        return CartItemDto.builder()
                .cartItemId(1L)
                .productId(1L)
                .quantity(1L)
                .cartId(1L)
                .build();
    }

    private OrderDto getMockOrderDto() {
        return OrderDto.builder()
                .orderId(1L)
                .orderDate(LocalDateTime.now())
                .orderDescription("Test Order")
                .totalAmount(100.0)
                .orderStatus(OrderStatus.PREPARING)
                .cartDto(getMockCartDto())
                .build();
    }

    private PaymentDto getMockPaymentDto() {
        return PaymentDto.builder()
                .paymentId(1L)
                .isPayed(true)
                .paymentStatus(PaymentStatus.COMPLETED)
                .orderDto(getMockOrderDto())
                .build();
    }

    private ProductDto getMockProductDto() {
        return ProductDto.builder()
                .productId(1L)
                .productTitle("Test Product")
                .imageUrl("http://example.com/image.jpg")
                .sku("SKU123")
                .unitPrice(100.0)
                .quantity(10L)
                .cartItemDtos(new HashSet<>())
                .build();
    }

    private UserDto getMockUserDto() {
        return UserDto.builder()
                .userId(1L)
                .firstName("Test")
                .lastName("User")
                .email("test@example.com")
                .imageUrl("http://example.com/image.jpg")
                .phone("1234567890")
                .build();
    }

}
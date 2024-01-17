package com.ecommerce.orderservice.service.impl;

import com.ecommerce.orderservice.dto.*;
import com.ecommerce.orderservice.entity.Order;
import com.ecommerce.orderservice.entity.enums.OrderStatus;
import com.ecommerce.orderservice.entity.enums.PaymentStatus;
import com.ecommerce.orderservice.exception.OrderNotFoundException;
import com.ecommerce.orderservice.mapper.OrderMapper;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    private CartDto cartDto;
    private CartItemDto cartItemDto;
    private OrderDto orderDto;
    private PaymentDto paymentDto;
    private ProductDto productDto;
    private UserDto userDto;

    @Test
    @DisplayName("Create Order - Success")
    void shouldCreateOrderSuccessfully() {
        when(orderRepository.save(any(Order.class))).thenReturn(OrderMapper.mapToEntity(orderDto));

        OrderDto result = orderService.createOrder(orderDto);

        assertNotNull(result);
        assertEquals(orderDto.orderId(), result.orderId());
    }

    @Test
    @DisplayName("Create Order - Failure")
    void shouldThrowOrderNotFoundExceptionWhenOrderDtoIsNull() {
        assertThrows(OrderNotFoundException.class, () -> orderService.createOrder(null));
    }

    @Test
    @DisplayName("Update Order - Success")
    void shouldUpdateOrderSuccessfully() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(OrderMapper.mapToEntity(orderDto)));
        when(orderRepository.save(any(Order.class))).thenReturn(OrderMapper.mapToEntity(orderDto));

        OrderDto result = orderService.updateOrder(orderDto.orderId(), orderDto);

        assertNotNull(result);
        assertEquals(orderDto.orderId(), result.orderId());
    }

    @Test
    @DisplayName("Update Order - Failure")
    void shouldThrowOrderNotFoundExceptionWhenOrderDoesNotExist2() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderService.updateOrder(orderDto.orderId(), orderDto));
    }

    @Test
    @DisplayName("Get Order By Id - Success")
    void shouldGetOrderByIdSuccessfully() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(OrderMapper.mapToEntity(orderDto)));

        OrderDto result = orderService.getOrderById(orderDto.orderId());

        assertNotNull(result);
        assertEquals(orderDto.orderId(), result.orderId());
    }

    @Test
    @DisplayName("Get Order By Id - Failure")
    void shouldThrowOrderNotFoundExceptionWhenOrderDoesNotExist3() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderService.getOrderById(orderDto.orderId()));
    }

    @Test
    @DisplayName("Get Orders By Date - Success")
    void shouldGetOrdersByDateSuccessfully() {
        LocalDate startDate = LocalDate.now().minusDays(5);
        LocalDate endDate = LocalDate.now();
        List<Order> orders = Arrays.asList(OrderMapper.mapToEntity(orderDto));

        when(orderRepository.findOrdersByDateRange(any(LocalDate.class), any(LocalDate.class))).thenReturn(orders);

        List<OrderDto> result = orderService.getOrdersByDate(startDate, endDate);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(orders.size(), result.size());
    }

    @Test
    @DisplayName("Get Orders By Date - No Orders Found")
    void shouldReturnEmptyListWhenNoOrdersFound() {
        LocalDate startDate = LocalDate.now().minusDays(5);
        LocalDate endDate = LocalDate.now();

        when(orderRepository.findOrdersByDateRange(any(LocalDate.class), any(LocalDate.class))).thenReturn(new ArrayList<>());

        List<OrderDto> result = orderService.getOrdersByDate(startDate, endDate);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Cancel Order - Success")
    void shouldCancelOrderSuccessfully() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(OrderMapper.mapToEntity(orderDto)));

        orderService.cancelOrder(orderDto.orderId());

        verify(orderRepository).save(any(Order.class));
    }

    @Test
    @DisplayName("Cancel Order - Failure")
    void shouldThrowOrderNotFoundExceptionWhenOrderDoesNotExist() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderService.cancelOrder(orderDto.orderId()));
    }

    @BeforeEach
    void setUp() {
        cartDto = getMockCartDto();
        cartItemDto = getMockCartItemDto();
        orderDto = getMockOrderDto();
        paymentDto = getMockPaymentDto();
        productDto = getMockProductDto();
        userDto = getMockUserDto();
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
                .email("test@email.com")
                .imageUrl("http://example.com/image.jpg")
                .phone("1234567890")
                .build();
    }
}
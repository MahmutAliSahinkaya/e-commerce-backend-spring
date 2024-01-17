package com.ecommerce.orderservice.controller;

import com.ecommerce.orderservice.dto.CartDto;
import com.ecommerce.orderservice.dto.OrderDto;
import com.ecommerce.orderservice.entity.enums.OrderStatus;
import com.ecommerce.orderservice.service.impl.OrderServiceImpl;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
@ContextConfiguration(classes = {OrderController.class})
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderServiceImpl orderService;

    @Autowired
    private ObjectMapper objectMapper;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Create Order - Should Return Created")
    void createOrder_ShouldReturnCreated() throws Exception {
        OrderDto orderDto = getMockOrderDto();
        when(orderService.createOrder(any(OrderDto.class))).thenReturn(orderDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.orderId").value(orderDto.orderId()));
    }

    @Test
    @DisplayName("Update Order - Should Return Updated Order")
    void updateOrder_ShouldReturnUpdatedOrder() throws Exception {
        Long orderId = 1L;
        OrderDto orderDto = getMockOrderDto();
        when(orderService.updateOrder(anyLong(), any(OrderDto.class))).thenReturn(orderDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/orders/{orderId}", orderId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value(orderDto.orderId()));
    }

    @Test
    @DisplayName("Get Order By Id - Should Return Order")
    void getOrderById_ShouldReturnOrder() throws Exception {
        Long orderId = 1L;
        OrderDto orderDto = getMockOrderDto();
        when(orderService.getOrderById(anyLong())).thenReturn(orderDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/orders/{orderId}", orderId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value(orderDto.orderId()));
    }

    @Test
    @DisplayName("Get Orders By Date - Should Return Orders")
    void getOrdersByDate_ShouldReturnOrders() throws Exception {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now();
        List<OrderDto> orderDtos = Arrays.asList(getMockOrderDto(), getMockOrderDto());
        when(orderService.getOrdersByDate(any(LocalDate.class), any(LocalDate.class))).thenReturn(orderDtos);

        mockMvc.perform(MockMvcRequestBuilders.get("/orders/by_date")
                        .param("startDate", startDate.toString())
                        .param("endDate", endDate.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(orderDtos.size()));
    }

    @Test
    @DisplayName("Cancel Order - Should Return No Content")
    void cancelOrder_ShouldReturnNoContent() throws Exception {
        Long orderId = 1L;
        doNothing().when(orderService).cancelOrder(anyLong());

        mockMvc.perform(MockMvcRequestBuilders.delete("/orders/{orderId}", orderId))
                .andExpect(status().isNoContent());
    }

    private CartDto getMockCartDto() {
        return CartDto.builder()
                .cartId(1L)
                .userId(1L)
                .orderDtos(new HashSet<>())
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
}
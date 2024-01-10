package com.ecommerce.orderservice.service;

import com.ecommerce.orderservice.dto.OrderDto;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {

    OrderDto createOrder(OrderDto orderDto);

    OrderDto updateOrder(Long orderId, OrderDto orderDto);

    OrderDto getOrderById(Long orderId);

    List<OrderDto> getOrdersByDate(LocalDate startDate, LocalDate endDate);

    void cancelOrder(Long orderId);

}

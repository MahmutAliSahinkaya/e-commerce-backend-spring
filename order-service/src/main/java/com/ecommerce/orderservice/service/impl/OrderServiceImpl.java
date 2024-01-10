package com.ecommerce.orderservice.service.impl;

import com.ecommerce.orderservice.dto.OrderDto;
import com.ecommerce.orderservice.entity.Order;
import com.ecommerce.orderservice.entity.enums.OrderStatus;
import com.ecommerce.orderservice.exception.OrderNotFoundException;
import com.ecommerce.orderservice.mapper.OrderMapper;
import com.ecommerce.orderservice.repository.OrderRepository;
import com.ecommerce.orderservice.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        log.info("Creating order: {}", orderDto);
        if (orderDto == null) {
            log.error("OrderDto cannot be null");
            throw new OrderNotFoundException("OrderDto cannot be null");
        }

        Order order = OrderMapper.mapToEntity(orderDto);
        Order savedOrder = orderRepository.save(order);
        log.info("Order created: {}", savedOrder);

        return OrderMapper.mapToDto(savedOrder);
    }

    @Override
    public OrderDto updateOrder(Long orderId, OrderDto orderDto) {
        log.info("Updating order with id {}: {}", orderId, orderDto);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> {
                    log.error("Order with id {} not found", orderId);
                    return new OrderNotFoundException("Order with id " + orderId + " not found");
                });

        Order updatedOrder = OrderMapper.mapToEntity(orderDto);
        updatedOrder.setOrderId(orderId);
        orderRepository.save(updatedOrder);
        log.info("Order updated: {}", updatedOrder);

        return OrderMapper.mapToDto(updatedOrder);
    }

    @Override
    public OrderDto getOrderById(Long orderId) {
        log.info("Getting order by id: {}", orderId);
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isPresent()) {
            log.info("Order found: {}", order.get());
            return OrderMapper.mapToDto(order.get());
        } else {
            log.error("Order with id {} not found", orderId);
            throw new OrderNotFoundException("Order with id " + orderId + " not found");
        }
    }

    @Override
    public List<OrderDto> getOrdersByDate(LocalDate startDate, LocalDate endDate) {
        log.info("Getting orders by date range: {} - {}", startDate, endDate);
        List<Order> orders = orderRepository.findOrdersByDateRange(startDate, endDate);
        log.info("Found {} orders", orders.size());

        return orders.stream()
                .map(OrderMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void cancelOrder(Long orderId) {
        log.info("Cancelling order with id: {}", orderId);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> {
                    log.error("Order with id {} not found", orderId);
                    return new OrderNotFoundException("Order with id " + orderId + " not found");
                });
        order.setOrderStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
        log.info("Order cancelled: {}", order);
    }
}

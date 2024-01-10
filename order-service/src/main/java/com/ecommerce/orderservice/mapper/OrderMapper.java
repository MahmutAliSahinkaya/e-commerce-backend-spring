package com.ecommerce.orderservice.mapper;

import com.ecommerce.orderservice.dto.CartDto;
import com.ecommerce.orderservice.dto.OrderDto;
import com.ecommerce.orderservice.entity.Cart;
import com.ecommerce.orderservice.entity.Order;

public class OrderMapper {

    public static OrderDto mapToDto(Order order) {
        return OrderDto.builder()
                .orderId(order.getOrderId())
                .orderDate(order.getOrderDate())
                .orderDescription(order.getOrderDescription())
                .totalAmount(order.getTotalAmount())
                .orderStatus(order.getOrderStatus())
                .cartDto(
                        CartDto.builder()
                                .cartId(order.getCart().getCartId())
                                .build())
                .build();
    }

    public static Order mapToEntity(OrderDto orderDto) {
        return Order.builder()
                .orderId(orderDto.orderId())
                .orderDate(orderDto.orderDate())
                .orderDescription(orderDto.orderDescription())
                .totalAmount(orderDto.totalAmount())
                .orderStatus(orderDto.orderStatus())
                .cart(
                        Cart.builder()
                                .cartId(orderDto.cartDto().cartId())
                                .build())
                .build();
    }
}

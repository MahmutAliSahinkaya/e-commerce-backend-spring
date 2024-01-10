package com.ecommerce.orderservice.mapper;

import com.ecommerce.orderservice.dto.CartDto;
import com.ecommerce.orderservice.dto.UserDto;
import com.ecommerce.orderservice.entity.Cart;

public class CartMapper {

    public static CartDto mapToDto(Cart cart) {
        return CartDto.builder()
                .cartId(cart.getCartId())
                .userId(cart.getUserId())
                .userDto(
                        UserDto.builder()
                                .userId(cart.getUserId())
                                .build())
                .build();
    }

    public static Cart mapToEntity(CartDto cartDto) {
        return Cart.builder()
                .cartId(cartDto.cartId())
                .userId(cartDto.userId())
                .build();
    }
}
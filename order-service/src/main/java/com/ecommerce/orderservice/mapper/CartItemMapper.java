package com.ecommerce.orderservice.mapper;

import com.ecommerce.orderservice.dto.CartItemDto;
import com.ecommerce.orderservice.entity.Cart;
import com.ecommerce.orderservice.entity.CartItem;

public class CartItemMapper {
    public static CartItemDto mapToDto(CartItem cartItem) {
        return CartItemDto.builder()
                .cartItemId(cartItem.getCartItemId())
                .productId(cartItem.getProductId())
                .quantity(cartItem.getQuantity())
                .cartId(cartItem.getCart().getCartId())
                .build();
    }

    public static CartItem mapToEntity(CartItemDto cartItemDto) {
        return CartItem.builder()
                .cartItemId(cartItemDto.cartItemId())
                .productId(cartItemDto.productId())
                .quantity(cartItemDto.quantity())
                .cart(Cart.builder().cartId(cartItemDto.cartId()).build())
                .build();
    }
}

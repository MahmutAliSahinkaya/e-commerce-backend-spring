package com.ecommerce.orderservice.service;

import com.ecommerce.orderservice.dto.CartItemDto;

public interface CartService {

    void addItemToCart(CartItemDto cartItemDto);

    CartItemDto updateCartItem(Long cartItemId, CartItemDto cartItemDto);

    void removeItemFromCart(Long cartItemId);

}

package com.ecommerce.orderservice.service.impl;

import com.ecommerce.orderservice.dto.CartItemDto;
import com.ecommerce.orderservice.entity.Cart;
import com.ecommerce.orderservice.entity.CartItem;
import com.ecommerce.orderservice.exception.CartItemNotFoundException;
import com.ecommerce.orderservice.mapper.CartItemMapper;
import com.ecommerce.orderservice.repository.CartItemRepository;
import com.ecommerce.orderservice.repository.CartRepository;
import com.ecommerce.orderservice.service.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    private static final Logger log = LoggerFactory.getLogger(CartServiceImpl.class);

    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;

    public CartServiceImpl(CartItemRepository cartItemRepository,
                           CartRepository cartRepository) {
        this.cartItemRepository = cartItemRepository;
        this.cartRepository = cartRepository;
    }


    @Override
    public void addItemToCart(CartItemDto cartItemDto) {
        log.info("Adding item to cart: {}", cartItemDto);
        if (cartItemDto == null) {
            log.error("CartItemDto cannot be null");
            throw new CartItemNotFoundException("CartItemDto cannot be null");
        }

        CartItem cartItem = CartItemMapper.mapToEntity(cartItemDto);
        Cart cart = cartRepository.findById(cartItemDto.cartId())
                .orElseThrow(() -> {
                    log.error("Cart with id {} not found", cartItemDto.cartId());
                    return new CartItemNotFoundException("Cart with id " + cartItemDto.cartId() + " not found");
                });
        cartItem.setCart(cart);
        cartItemRepository.save(cartItem);
    }


    @Override
    public CartItemDto updateCartItem(Long cartItemId, CartItemDto cartItemDto) {
        log.info("Updating cart item with id {}: {}", cartItemId, cartItemDto);
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> {
                    log.error("CartItem with id {} not found", cartItemId);
                    return new CartItemNotFoundException("CartItem with id " + cartItemId + " not found");
                });

        CartItem updatedCartItem = CartItemMapper.mapToEntity(cartItemDto);
        updatedCartItem.setCartItemId(cartItemId);
        Cart cart = cartRepository.findById(cartItemDto.cartId())
                .orElseThrow(() -> {
                    log.error("Cart with id {} not found", cartItemDto.cartId());
                    return new CartItemNotFoundException("Cart with id " + cartItemDto.cartId() + " not found");
                });
        updatedCartItem.setCart(cart);
        cartItemRepository.save(updatedCartItem);

        return CartItemMapper.mapToDto(updatedCartItem);
    }


    @Override
    public void removeItemFromCart(Long cartItemId) {
        log.info("Removing item with id {} from cart", cartItemId);
        Optional<CartItem> optionalCartItem = cartItemRepository.findById(cartItemId);
        CartItem cartItem = optionalCartItem
                .orElseThrow(() -> {
                    log.error("CartItem with id {} not found", cartItemId);
                    return new CartItemNotFoundException("CartItem with id " + cartItemId + " not found");
                });
        cartItemRepository.delete(cartItem);
    }
}

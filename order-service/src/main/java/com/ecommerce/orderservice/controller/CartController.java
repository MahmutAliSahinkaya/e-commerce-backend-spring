package com.ecommerce.orderservice.controller;

import com.ecommerce.orderservice.dto.CartItemDto;
import com.ecommerce.orderservice.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/items")
    public ResponseEntity<Void> addItemToCart(@RequestBody CartItemDto cartItemDto) {
        cartService.addItemToCart(cartItemDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/items/{cartItemId}")
    public ResponseEntity<CartItemDto> updateCartItem(@PathVariable Long cartItemId,
                                                      @RequestBody CartItemDto cartItemDto) {
        CartItemDto updatedCartItem = cartService.updateCartItem(cartItemId, cartItemDto);
        return new ResponseEntity<>(updatedCartItem, HttpStatus.OK);
    }

    @DeleteMapping("/items/{cartItemId}")
    public ResponseEntity<Void> removeItemFromCart(@PathVariable Long cartItemId) {
        cartService.removeItemFromCart(cartItemId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}

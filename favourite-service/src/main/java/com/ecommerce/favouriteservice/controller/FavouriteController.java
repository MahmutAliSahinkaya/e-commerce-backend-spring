package com.ecommerce.favouriteservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/favourites")
public class FavouriteController {

    private final FavouriteService favouriteService;

    @Autowired
    public FavouriteController(FavouriteService favouriteService) {
        this.favouriteService = favouriteService;
    }

    @PostMapping("/{userId}/products/{productId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> addProductToFavourites(@PathVariable Long userId, @PathVariable Long productId) {
        favouriteService.addProductToFavourites(userId, productId);
        return ResponseEntity.status(HttpStatus.CREATED).body("Product added to favourites");
    }

    @DeleteMapping("/{userId}/products/{productId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> removeProductFromFavourites(@PathVariable Long userId, @PathVariable Long productId) {
        favouriteService.removeProductFromFavourites(userId, productId);
        return ResponseEntity.ok("Product removed from favourites");
    }

    @GetMapping("/{userId}/products")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<ProductDto>> getFavouriteProducts(@PathVariable Long userId) {
        List<ProductDto> favouriteProducts = favouriteService.getFavouriteProducts(userId);
        return ResponseEntity.ok(favouriteProducts);
    }

}

package com.ecommerce.favouriteservice.controller;

import com.ecommerce.favouriteservice.dto.ProductDto;
import com.ecommerce.favouriteservice.service.FavouriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favourites")
public class FavouriteController {

    private final FavouriteService favouriteService;

    @Autowired
    public FavouriteController(FavouriteService favouriteService) {
        this.favouriteService = favouriteService;
    }

    @PostMapping("/{userId}/products/{productId}")
    public ResponseEntity<String> addProductToFavourites(@PathVariable Long userId, @PathVariable Long productId) {
        favouriteService.addProductToFavourites(userId, productId);
        return ResponseEntity.status(HttpStatus.CREATED).body("Product added to favourites");
    }

    @DeleteMapping("/{userId}/products/{productId}")
    public ResponseEntity<String> removeProductFromFavourites(@PathVariable Long userId, @PathVariable Long productId) {
        favouriteService.removeProductFromFavourites(userId, productId);
        return ResponseEntity.ok("Product removed from favourites");
    }

    @GetMapping("/{userId}/products")
    public ResponseEntity<List<ProductDto>> getFavouriteProducts(@PathVariable Long userId) {
        List<ProductDto> favouriteProducts = favouriteService.getFavouriteProducts(userId);
        return ResponseEntity.ok(favouriteProducts);
    }

}

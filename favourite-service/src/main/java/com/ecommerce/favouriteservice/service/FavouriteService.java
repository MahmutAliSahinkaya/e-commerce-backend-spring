package com.ecommerce.favouriteservice.service;

import com.ecommerce.favouriteservice.dto.ProductDto;

import java.util.List;

public interface FavouriteService {

    void addProductToFavourites(Long userId, Long productId);

    void removeProductFromFavourites(Long userId, Long productId);

    List<ProductDto> getFavouriteProducts(Long userId);

}

package com.ecommerce.favouriteservice.mapper;

import com.ecommerce.favouriteservice.dto.FavouriteDto;
import com.ecommerce.favouriteservice.dto.ProductDto;
import com.ecommerce.favouriteservice.dto.UserDto;
import com.ecommerce.favouriteservice.entity.Favourite;

public class FavouriteMapper {

    public static FavouriteDto mapToDto(Favourite favourite) {
        return FavouriteDto.builder()
                .userId(favourite.getUserId())
                .productId(favourite.getProductId())
                .likeDate(favourite.getLikeDate())
                .userDto(UserDto.builder().userId(favourite.getUserId()).build())
                .productDto(ProductDto.builder().productId(favourite.getProductId()).build())
                .build();
    }

    public static Favourite mapToEntity(FavouriteDto favouriteDto) {
        return Favourite.builder()
                .userId(favouriteDto.userId())
                .productId(favouriteDto.productId())
                .likeDate(favouriteDto.likeDate())
                .build();
    }
}

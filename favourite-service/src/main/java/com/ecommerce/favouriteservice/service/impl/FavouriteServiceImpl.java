package com.ecommerce.favouriteservice.service.impl;

import com.ecommerce.favouriteservice.dto.ProductDto;
import com.ecommerce.favouriteservice.dto.UserDto;
import com.ecommerce.favouriteservice.entity.Favourite;
import com.ecommerce.favouriteservice.exception.FavouriteNotFoundException;
import com.ecommerce.favouriteservice.exception.ProductNotFoundException;
import com.ecommerce.favouriteservice.exception.UserNotFoundException;
import com.ecommerce.favouriteservice.repository.FavouriteRepository;
import com.ecommerce.favouriteservice.service.FavouriteService;
import com.ecommerce.favouriteservice.service.client.ProductServiceClient;
import com.ecommerce.favouriteservice.service.client.UserServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class FavouriteServiceImpl implements FavouriteService {

    private static final Logger log = LoggerFactory.getLogger(FavouriteServiceImpl.class);

    private final FavouriteRepository favouriteRepository;
    private final UserServiceClient userServiceClient;
    private final ProductServiceClient productServiceClient;

    public FavouriteServiceImpl(FavouriteRepository favouriteRepository,
                                UserServiceClient userServiceClient,
                                ProductServiceClient productServiceClient) {
        this.favouriteRepository = favouriteRepository;
        this.userServiceClient = userServiceClient;
        this.productServiceClient = productServiceClient;
    }

    @Override
    public void addProductToFavourites(Long userId, Long productId) {
        log.info("Adding product with id {} to favourites for user with id {}", productId, userId);

        UserDto user = userServiceClient.getUserById(userId);
        if (user == null) {
            log.error("User with id {} does not exist", userId);
            throw new UserNotFoundException("User with id " + userId + " does not exist");
        }

        ProductDto product = productServiceClient.getProductById(productId);
        if (product == null) {
            log.error("Product with id {} does not exist", productId);
            throw new ProductNotFoundException("Product with id " + productId + " does not exist");
        }

        Collection<Object> existingFavourites = favouriteRepository.findByUserIdAndProductId(userId, productId);
        if (!existingFavourites.isEmpty()) {
            log.error("Product with id {} is already in the favourites of user with id {}", productId, userId);
            throw new FavouriteNotFoundException("Product with id " + productId + " is already in the favourites of user with id " + userId);
        }

        Favourite favourite = Favourite.builder()
                .userId(userId)
                .productId(productId)
                .likeDate(LocalDateTime.now())
                .build();
        favouriteRepository.save(favourite);
        log.info("Product with id {} added to favourites for user with id {}", productId, userId);
    }

    @Override
    public void removeProductFromFavourites(Long userId, Long productId) {
        log.info("Removing product with id {} from favourites for user with id {}", productId, userId);

        UserDto user = userServiceClient.getUserById(userId);
        if (user == null) {
            log.error("User with id {} does not exist", userId);
            throw new UserNotFoundException("User with id " + userId + " does not exist");
        }

        ProductDto product = productServiceClient.getProductById(productId);
        if (product == null) {
            log.error("Product with id {} does not exist", productId);
            throw new ProductNotFoundException("Product with id " + productId + " does not exist");
        }

        Collection<Object> existingFavourites = favouriteRepository.findByUserIdAndProductId(userId, productId);
        if (existingFavourites.isEmpty()) {
            log.error("Product with id {} is not in the favourites of user with id {}", productId, userId);
            throw new FavouriteNotFoundException("Product with id " + productId + " is not in the favourites of user with id " + userId);
        }

        for (Object favourite : existingFavourites) {
            favouriteRepository.delete((Favourite) favourite);
        }
        log.info("Product with id {} removed from favourites for user with id {}", productId, userId);
    }


    @Override
    public List<ProductDto> getFavouriteProducts(Long userId) {
        log.info("Getting favourite products for user with id {}", userId);

        UserDto user = userServiceClient.getUserById(userId);
        if (user == null) {
            log.error("User with id {} does not exist", userId);
            throw new UserNotFoundException("User with id " + userId + " does not exist");
        }

        List<Favourite> favourites = favouriteRepository.findByUserId(userId);

        List<ProductDto> favouriteProducts = new ArrayList<>();
        for (Favourite favourite : favourites) {
            ProductDto product = productServiceClient.getProductById(favourite.getProductId());
            favouriteProducts.add(product);
        }

        log.info("Found {} favourite products for user with id {}", favouriteProducts.size(), userId);
        return favouriteProducts;
    }
}

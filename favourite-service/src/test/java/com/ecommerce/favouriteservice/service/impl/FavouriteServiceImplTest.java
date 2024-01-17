package com.ecommerce.favouriteservice.service.impl;

import com.ecommerce.favouriteservice.dto.FavouriteDto;
import com.ecommerce.favouriteservice.dto.ProductDetailsDto;
import com.ecommerce.favouriteservice.dto.ProductDto;
import com.ecommerce.favouriteservice.dto.UserDto;
import com.ecommerce.favouriteservice.entity.Favourite;
import com.ecommerce.favouriteservice.entity.FavouriteId;
import com.ecommerce.favouriteservice.exception.FavouriteNotFoundException;
import com.ecommerce.favouriteservice.exception.ProductNotFoundException;
import com.ecommerce.favouriteservice.exception.UserNotFoundException;
import com.ecommerce.favouriteservice.repository.FavouriteRepository;
import com.ecommerce.favouriteservice.service.client.ProductServiceClient;
import com.ecommerce.favouriteservice.service.client.UserServiceClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FavouriteServiceImplTest {
    @Mock
    private FavouriteRepository favouriteRepository;

    @Mock
    private UserServiceClient userServiceClient;

    @Mock
    private ProductServiceClient productServiceClient;

    @InjectMocks
    private FavouriteServiceImpl favouriteService;

    private FavouriteDto favouriteDto;
    private ProductDetailsDto productDetailsDto;
    private ProductDto productDto;
    private UserDto userDto;
    private Favourite favourite;
    private FavouriteId favouriteId;

    @BeforeEach
    void setUp() {
        favouriteDto = getMockFavouriteDto();
        productDetailsDto = getMockProductDetailsDto();
        productDto = getMockProductDto();
        userDto = getMockUserDto();
        favourite = getMockFavourite();
        favouriteId = getMockFavouriteId();
    }


    @Test
    @DisplayName("Add Product To Favourites - Success")
    void shouldAddProductToFavouritesSuccessfully() {
        when(userServiceClient.getUserById(anyLong())).thenReturn(userDto);
        when(productServiceClient.getProductById(anyLong())).thenReturn(productDto);
        when(favouriteRepository.findByUserIdAndProductId(anyLong(), anyLong())).thenReturn(new ArrayList<>());
        when(favouriteRepository.save(any(Favourite.class))).thenReturn(favourite);

        favouriteService.addProductToFavourites(favouriteDto.userId(), favouriteDto.productId());

        verify(favouriteRepository).save(any(Favourite.class));
    }

    @Test
    @DisplayName("Add Product To Favourites - Failure due to non-existent User")
    void shouldThrowUserNotFoundExceptionWhenUserDoesNotExist() {
        when(userServiceClient.getUserById(anyLong())).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> favouriteService.addProductToFavourites(favouriteDto.userId(), favouriteDto.productId()));
    }

    @Test
    @DisplayName("Add Product To Favourites - Failure due to non-existent Product")
    void shouldThrowProductNotFoundExceptionWhenProductDoesNotExist() {
        when(userServiceClient.getUserById(anyLong())).thenReturn(userDto);
        when(productServiceClient.getProductById(anyLong())).thenReturn(null);

        assertThrows(ProductNotFoundException.class, () -> favouriteService.addProductToFavourites(favouriteDto.userId(), favouriteDto.productId()));
    }

    @Test
    @DisplayName("Add Product To Favourites - Failure due to already favourited Product")
    void shouldThrowFavouriteNotFoundExceptionWhenProductAlreadyFavourited() {
        when(userServiceClient.getUserById(anyLong())).thenReturn(userDto);
        when(productServiceClient.getProductById(anyLong())).thenReturn(productDto);
        when(favouriteRepository.findByUserIdAndProductId(anyLong(), anyLong())).thenReturn(List.of(favourite));

        assertThrows(FavouriteNotFoundException.class, () -> favouriteService.addProductToFavourites(favouriteDto.userId(), favouriteDto.productId()));
    }

    @Test
    @DisplayName("Remove Product From Favourites - Success")
    void shouldRemoveProductFromFavouritesSuccessfully() {
        when(userServiceClient.getUserById(anyLong())).thenReturn(userDto);
        when(productServiceClient.getProductById(anyLong())).thenReturn(productDto);
        when(favouriteRepository.findByUserIdAndProductId(anyLong(), anyLong())).thenReturn(List.of(favourite));

        favouriteService.removeProductFromFavourites(favouriteDto.userId(), favouriteDto.productId());

        verify(favouriteRepository).delete(any(Favourite.class));
    }

    @Test
    @DisplayName("Remove Product From Favourites - Failure due to non-existent User")
    void shouldThrowUserNotFoundExceptionWhenRemovingProductFromNonExistentUser() {
        when(userServiceClient.getUserById(anyLong())).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> favouriteService.removeProductFromFavourites(favouriteDto.userId(), favouriteDto.productId()));
    }

    @Test
    @DisplayName("Remove Product From Favourites - Failure due to non-existent Product")
    void shouldThrowProductNotFoundExceptionWhenRemovingNonExistentProduct() {
        when(userServiceClient.getUserById(anyLong())).thenReturn(userDto);
        when(productServiceClient.getProductById(anyLong())).thenReturn(null);

        assertThrows(ProductNotFoundException.class, () -> favouriteService.removeProductFromFavourites(favouriteDto.userId(), favouriteDto.productId()));
    }

    @Test
    @DisplayName("Remove Product From Favourites - Failure due to non-favourited Product")
    void shouldThrowFavouriteNotFoundExceptionWhenRemovingNonFavouritedProduct() {
        when(userServiceClient.getUserById(anyLong())).thenReturn(userDto);
        when(productServiceClient.getProductById(anyLong())).thenReturn(productDto);
        when(favouriteRepository.findByUserIdAndProductId(anyLong(), anyLong())).thenReturn(new ArrayList<>());

        assertThrows(FavouriteNotFoundException.class, () -> favouriteService.removeProductFromFavourites(favouriteDto.userId(), favouriteDto.productId()));
    }

    @Test
    @DisplayName("Get Favourite Products - Success")
    void shouldGetFavouriteProductsSuccessfully() {
        when(userServiceClient.getUserById(anyLong())).thenReturn(userDto);
        when(favouriteRepository.findByUserId(anyLong())).thenReturn(List.of(favourite));
        when(productServiceClient.getProductById(anyLong())).thenReturn(productDto);

        List<ProductDto> result = favouriteService.getFavouriteProducts(favouriteDto.userId());

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Get Favourite Products - Failure due to non-existent User")
    void shouldThrowUserNotFoundExceptionWhenGettingFavouriteProductsForNonExistentUser() {
        when(userServiceClient.getUserById(anyLong())).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> favouriteService.getFavouriteProducts(favouriteDto.userId()));
    }

    private FavouriteDto getMockFavouriteDto() {
        return FavouriteDto.builder()
                .userId(1L)
                .productId(1L)
                .likeDate(LocalDateTime.now())
                .userDto(getMockUserDto())
                .productDto(getMockProductDto())
                .build();
    }

    private ProductDetailsDto getMockProductDetailsDto() {
        return ProductDetailsDto.builder()
                .productId(1L)
                .productTitle("Test Product")
                .build();
    }

    private ProductDto getMockProductDto() {
        return ProductDto.builder()
                .productId(1L)
                .productTitle("Test Product")
                .imageUrl("http://example.com/image.jpg")
                .sku("SKU123")
                .priceUnit(100.0)
                .quantity(10L)
                .favouriteDtos(new HashSet<>())
                .build();
    }

    private UserDto getMockUserDto() {
        return UserDto.builder()
                .userId(1L)
                .firstName("Test")
                .lastName("User")
                .email("test@example.com")
                .imageUrl("http://example.com/image.jpg")
                .phone("1234567890")
                .favouriteDtos(new HashSet<>())
                .build();
    }

    private Favourite getMockFavourite() {
        return Favourite.builder()
                .userId(1L)
                .productId(1L)
                .likeDate(LocalDateTime.now())
                .build();
    }

    private FavouriteId getMockFavouriteId() {
        return FavouriteId.builder()
                .userId(1L)
                .productId(1L)
                .likeDate(LocalDateTime.now())
                .build();
    }
}
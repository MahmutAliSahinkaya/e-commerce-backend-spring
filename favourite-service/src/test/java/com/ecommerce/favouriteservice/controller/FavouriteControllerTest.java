package com.ecommerce.favouriteservice.controller;

import com.ecommerce.favouriteservice.dto.ProductDto;
import com.ecommerce.favouriteservice.service.impl.FavouriteServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FavouriteController.class)
@ContextConfiguration(classes = {FavouriteController.class})
class FavouriteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FavouriteServiceImpl favouriteService;

    @Autowired
    private ObjectMapper objectMapper;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Add Product to Favourites - Should Return Created")
    void addProductToFavourites_ShouldReturnCreated() throws Exception {
        Long userId = 1L;
        Long productId = 1L;
        doNothing().when(favouriteService).addProductToFavourites(anyLong(), anyLong());

        mockMvc.perform(MockMvcRequestBuilders.post("/favourites/{userId}/products/{productId}", userId, productId))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Remove Product from Favourites - Should Return Ok")
    void removeProductFromFavourites_ShouldReturnOk() throws Exception {
        Long userId = 1L;
        Long productId = 1L;
        doNothing().when(favouriteService).removeProductFromFavourites(anyLong(), anyLong());

        mockMvc.perform(MockMvcRequestBuilders.delete("/favourites/{userId}/products/{productId}", userId, productId))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Get Favourite Products - Should Return Products")
    void getFavouriteProducts_ShouldReturnProducts() throws Exception {
        Long userId = 1L;
        List<ProductDto> productDtos = Arrays.asList(getMockProductDto(), getMockProductDto());
        when(favouriteService.getFavouriteProducts(anyLong())).thenReturn(productDtos);

        mockMvc.perform(MockMvcRequestBuilders.get("/favourites/{userId}/products", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(productDtos.size()));
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
}
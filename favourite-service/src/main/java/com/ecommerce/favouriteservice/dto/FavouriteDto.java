package com.ecommerce.favouriteservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Builder
public record FavouriteDto (
        @NotNull(message = "Field must not be NULL")
        Long userId,
        @NotNull(message = "Field must not be NULL")
        Long productId,
        @NotNull(message = "Field must not be NULL")
        @DateTimeFormat(pattern = "dd-MM-yyyy__HH:mm:ss:SSSSSS")
        LocalDateTime likeDate,
        UserDto userDto,
        ProductDto productDto
) {
}

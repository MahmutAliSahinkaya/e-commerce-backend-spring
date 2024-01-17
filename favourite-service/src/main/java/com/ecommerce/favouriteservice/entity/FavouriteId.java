package com.ecommerce.favouriteservice.entity;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FavouriteId implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "User id cannot be null")
    private Long userId;

    @NotNull(message = "Product id cannot be null")
    private Long productId;

    @NotNull(message = "Like date cannot be null")
    @DateTimeFormat(pattern = "dd-MM-yyyy__HH:mm:ss:SSSSSS")
    private LocalDateTime likeDate;

}

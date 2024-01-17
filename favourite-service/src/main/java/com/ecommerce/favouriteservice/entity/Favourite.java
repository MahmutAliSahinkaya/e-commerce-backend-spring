package com.ecommerce.favouriteservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "favourites")
@IdClass(FavouriteId.class)
public class Favourite extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @NotNull(message = "User id cannot be null")
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Id
    @NotNull(message = "Product id cannot be null")
    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Id
    @NotNull(message = "Like date cannot be null")
    @Column(name = "like_date", nullable = false)
    @DateTimeFormat(pattern = "dd-MM-yyyy__HH:mm:ss:SSSSSS")
    private LocalDateTime likeDate;


}
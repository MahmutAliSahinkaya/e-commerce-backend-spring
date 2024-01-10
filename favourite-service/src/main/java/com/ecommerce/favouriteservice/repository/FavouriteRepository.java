package com.ecommerce.favouriteservice.repository;

import com.ecommerce.favouriteservice.entity.Favourite;
import com.ecommerce.favouriteservice.entity.FavouriteId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface FavouriteRepository extends JpaRepository<Favourite, FavouriteId> {
    List<Favourite> findByUserId(Long userId);

    @Query("SELECT f FROM Favourite f WHERE f.userId = :userId AND f.productId = :productId")
    Collection<Object> findByUserIdAndProductId(@Param("userId") Long userId, @Param("productId") Long productId);

}

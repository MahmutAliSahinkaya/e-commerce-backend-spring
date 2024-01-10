package com.ecommerce.productservice.repository;

import com.ecommerce.productservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {


    Optional<Product> findBySku(String sku);

    List<Product> findByProductTitleContaining(String keyword);

    List<Product> findByCategoryCategoryId(Long categoryId);


}

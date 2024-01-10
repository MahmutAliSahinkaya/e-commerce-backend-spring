package com.ecommerce.productservice.repository;

import com.ecommerce.productservice.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByCategoryTitle(String categoryTitle);
    List<Category> findByCategoryId(Long categoryId);


}

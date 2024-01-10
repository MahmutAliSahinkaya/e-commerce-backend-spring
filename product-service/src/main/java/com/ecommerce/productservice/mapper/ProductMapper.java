package com.ecommerce.productservice.mapper;

import com.ecommerce.productservice.dto.CategoryDto;
import com.ecommerce.productservice.dto.ProductDetailsDto;
import com.ecommerce.productservice.dto.ProductDto;
import com.ecommerce.productservice.entity.Category;
import com.ecommerce.productservice.entity.Product;

import java.util.List;
import java.util.stream.Collectors;

public class ProductMapper {

    public static ProductDto toDto(Product product) {
        return ProductDto.builder()
                .productId(product.getProductId())
                .productTitle(product.getProductTitle())
                .imageUrl(product.getImageUrl())
                .sku(product.getSku())
                .priceUnit(product.getPriceUnit())
                .quantity(product.getQuantity())
                .categoryDto(
                        CategoryDto.builder()
                                .categoryId(product.getCategory().getCategoryId())
                                .categoryTitle(product.getCategory().getCategoryTitle())
                                .imageUrl(product.getCategory().getImageUrl())
                                .build())
                .build();
    }

    public static Product toEntity(ProductDto productDto) {
        return Product.builder()
                .productId(productDto.productId())
                .productTitle(productDto.productTitle())
                .imageUrl(productDto.imageUrl())
                .sku(productDto.sku())
                .priceUnit(productDto.priceUnit())
                .quantity(productDto.quantity())
                .category(
                        Category.builder()
                                .categoryId(productDto.categoryDto().categoryId())
                                .categoryTitle(productDto.categoryDto().categoryTitle())
                                .imageUrl(productDto.categoryDto().imageUrl())
                                .build())
                .build();
    }

    public static ProductDetailsDto toProductDetailsDto(Product product) {
        return ProductDetailsDto.builder()
                .productId(product.getProductId())
                .productTitle(product.getProductTitle())
                .build();
    }

    public static List<ProductDto> toDtoList(List<Product> products) {
        return products.stream()
                .map(ProductMapper::toDto)
                .collect(Collectors.toList());
    }
}

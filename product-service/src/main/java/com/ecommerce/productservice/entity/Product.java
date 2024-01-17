package com.ecommerce.productservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
@EqualsAndHashCode(callSuper = true, exclude = {"category"})
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", unique = true, nullable = false, updatable = false)
    private Long productId;

    @NotBlank(message = "Product title is required")
    @Column(name = "product_title")
    private String productTitle;

    @Column(name = "image_url")
    private String imageUrl;

    @NotBlank(message = "Product SKU is required")
    @Column(unique = true)
    private String sku;

    @NotNull(message = "Product Unit Price is required")
    @Min(value = 0, message = "Product Unit Price must be greater than 0")
    @Column(name = "price_unit", columnDefinition = "decimal")
    private Double priceUnit;

    @NotNull(message = "Product Quantity is required")
    @Min(value = 0, message = "Product Quantity must be greater than 0")
    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

}

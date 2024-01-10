package com.ecommerce.productservice.entity;

import jakarta.persistence.*;
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

    @Column(name = "product_title")
    private String productTitle;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(unique = true)
    private String sku;

    @Column(name = "price_unit", columnDefinition = "decimal")
    private Double priceUnit;

    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

}

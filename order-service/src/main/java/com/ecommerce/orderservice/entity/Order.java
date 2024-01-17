package com.ecommerce.orderservice.entity;

import com.ecommerce.orderservice.entity.enums.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
@EqualsAndHashCode(callSuper = true, exclude = {"cart"})
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", unique = true, nullable = false, updatable = false)
    private Long orderId;

    @DateTimeFormat(pattern = "dd-MM-yyyy__HH:mm:ss:SSSSSS")
    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Column(name = "order_description")
    private String orderDescription;

    @NotNull(message = "Total amount cannot be null")
    @Min(value = 0, message = "Total amount cannot be less than 0")
    @Column(name = "total_amount", columnDefinition = "decimal")
    private Double totalAmount;

    @NotNull(message = "Order status cannot be null")
    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cart_id")
    private Cart cart;

}

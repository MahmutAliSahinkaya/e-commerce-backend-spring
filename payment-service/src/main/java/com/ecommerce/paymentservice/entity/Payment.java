package com.ecommerce.paymentservice.entity;

import com.ecommerce.paymentservice.entity.enums.PaymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payments")
@EqualsAndHashCode(callSuper = true)
public class Payment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id", unique = true, nullable = false, updatable = false)
    private Long paymentId;

    @NotNull(message = "Order id cannot be null")
    @Column(name = "order_id")
    private Long orderId;

    @NotNull(message = "User id cannot be null")
    @Column(name = "user_id")
    private Long userId;

    @NotNull(message = "Payed info cannot be null")
    @Column(name = "is_payed")
    private Boolean isPayed;

    @NotNull(message = "Payment status cannot be null")
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;

    @NotNull(message = "Communication sw cannot be null")
    @Column(name = "communication_sw")
    private Boolean communicationSw;
}

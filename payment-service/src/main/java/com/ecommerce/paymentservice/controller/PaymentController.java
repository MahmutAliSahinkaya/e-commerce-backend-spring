package com.ecommerce.paymentservice.controller;

import com.ecommerce.paymentservice.dto.PaymentDto;
import com.ecommerce.paymentservice.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/process")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Void> processPayment(@RequestBody PaymentDto paymentDto) {
        paymentService.processPayment(paymentDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/validate/{paymentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Boolean> validatePayment(@PathVariable Long paymentId) {
        boolean isValid = paymentService.validatePayment(paymentId);
        return ResponseEntity.ok(isValid);
    }

    @GetMapping("/details/{userId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<PaymentDto> getPaymentByUserId(@PathVariable Long userId) {
        PaymentDto paymentDetails = paymentService.getPaymentByUserId(userId);
        return ResponseEntity.ok(paymentDetails);
    }

}

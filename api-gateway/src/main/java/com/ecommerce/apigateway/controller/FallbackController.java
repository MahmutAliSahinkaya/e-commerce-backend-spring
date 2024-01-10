package com.ecommerce.apigateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {

    @GetMapping("/orderServiceFallBack")
    public String orderServiceFallback() {
        return "Order or Cart Services are down!";
    }

    @GetMapping("/paymentServiceFallBack")
    public String paymentServiceFallback() {
        return "Payment Service is down!";
    }

    @GetMapping("/productServiceFallBack")
    public String productServiceFallback() {
        return "Product or Category Services are down";
    }

    @GetMapping("/userServiceFallBack")
    public String userServiceFallback() {
        return "User Service is down!";
    }

    @GetMapping("/reportServiceFallBack")
    public String reportServiceFallback() {
        return "Report Service is down!";
    }

    @GetMapping("/shippingServiceFallBack")
    public String shippingServiceFallback() {
        return "Shipping Service is down!";
    }

    @GetMapping("/favouriteServiceFallBack")
    public String favouriteServiceFallback() {
        return "Favourite Service is down!";
    }
}

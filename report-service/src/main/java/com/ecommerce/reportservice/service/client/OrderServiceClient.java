package com.ecommerce.reportservice.service.client;

import com.ecommerce.reportservice.dto.OrderDto;
import com.ecommerce.reportservice.service.fallback.OrderFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Primary
@FeignClient(name = "order-service", url = "http://localhost:8082", fallback = OrderFallback.class)
public interface OrderServiceClient {

    @GetMapping(value = "/orders/by_date", consumes = "application/json")
    List<OrderDto> getOrdersByDate(@RequestParam("startDate") LocalDate startDate,
                                   @RequestParam("endDate") LocalDate endDate);

}

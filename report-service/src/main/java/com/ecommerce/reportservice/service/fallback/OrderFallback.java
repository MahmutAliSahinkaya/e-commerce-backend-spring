package com.ecommerce.reportservice.service.fallback;

import com.ecommerce.reportservice.dto.OrderDto;
import com.ecommerce.reportservice.service.client.OrderServiceClient;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Component
public class OrderFallback implements OrderServiceClient {


    @Override
    public List<OrderDto> getOrdersByDate(LocalDate startDate, LocalDate endDate) {
        return getOrdersByDateFallback(new Exception("Order Service is down"));
    }

    private List<OrderDto> getOrdersByDateFallback(Throwable throwable) {

        System.out.println(throwable.getMessage());
        return Collections.emptyList();
    }

}

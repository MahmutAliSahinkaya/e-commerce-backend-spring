package com.ecommerce.reportservice.service.impl;

import com.ecommerce.reportservice.dto.OrderDto;
import com.ecommerce.reportservice.dto.ProductDto;
import com.ecommerce.reportservice.dto.ReportItemDto;
import com.ecommerce.reportservice.exception.OrderNotFoundException;
import com.ecommerce.reportservice.exception.ProductNotFoundException;
import com.ecommerce.reportservice.service.ReportService;
import com.ecommerce.reportservice.service.client.OrderServiceClient;
import com.ecommerce.reportservice.service.client.ProductServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    private static final Logger log = LoggerFactory.getLogger(ReportService.class);

    private final OrderServiceClient orderServiceClient;
    private final ProductServiceClient productServiceClient;

    public ReportServiceImpl(OrderServiceClient orderServiceClient,
                             ProductServiceClient productServiceClient) {
        this.orderServiceClient = orderServiceClient;
        this.productServiceClient = productServiceClient;
    }

    @Override
    public List<ReportItemDto> generateSalesReport(LocalDate startDate, LocalDate endDate) {
        log.info("Entering generateSalesReport with startDate: {}, endDate: {}", startDate, endDate);
        List<OrderDto> orders = orderServiceClient.getOrdersByDate(startDate, endDate);

        if (orders.isEmpty()) {
            throw new OrderNotFoundException("No orders found");
        }

        List<ReportItemDto> reportItems = orders.stream().map(order -> {
            ProductDto product = productServiceClient.getProductById(order.productId());

            ReportItemDto reportItem = new ReportItemDto(
                    product.name(),
                    order.total(),
                    order.total() - order.shippingCost(),
                    1,
                    1
            );
            return reportItem;
        }).collect(Collectors.toList());

        log.info("Exiting generateSalesReport with result: {}", reportItems);
        return reportItems;
    }


    @Override
    public List<ReportItemDto> generateInventoryReport() {
        log.info("Entering generateInventoryReport");
        List<ProductDto> products = productServiceClient.getAllProducts();

        if (products.isEmpty()) {
            throw new ProductNotFoundException("No products found");
        }

        List<ReportItemDto> reportItems = products.stream()
                .map(product -> new ReportItemDto(
                        product.name(),
                        0,
                        0,
                        0,
                        1
                ))
                .collect(Collectors.toList());

        log.info("Exiting generateInventoryReport with result: {}", reportItems);
        return reportItems;
    }
}

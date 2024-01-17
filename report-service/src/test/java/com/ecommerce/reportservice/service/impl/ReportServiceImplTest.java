package com.ecommerce.reportservice.service.impl;

import com.ecommerce.reportservice.dto.OrderDto;
import com.ecommerce.reportservice.dto.ProductDto;
import com.ecommerce.reportservice.dto.ReportItemDto;
import com.ecommerce.reportservice.exception.OrderNotFoundException;
import com.ecommerce.reportservice.exception.ProductNotFoundException;
import com.ecommerce.reportservice.service.client.OrderServiceClient;
import com.ecommerce.reportservice.service.client.ProductServiceClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReportServiceImplTest {

    @Mock
    private OrderServiceClient orderServiceClient;

    @Mock
    private ProductServiceClient productServiceClient;

    @InjectMocks
    private ReportServiceImpl reportService;

    private OrderDto orderDto;
    private ProductDto productDto;
    private ReportItemDto reportItemDto;

    @BeforeEach
    void setUp() {
        orderDto = getMockOrderDto();
        productDto = getMockProductDto();
        reportItemDto = getMockReportItemDto();
    }

    @Test
    @DisplayName("Generate Sales Report Success")
    void shouldGenerateSalesReportSuccessfully() {
        when(orderServiceClient.getOrdersByDate(any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(Arrays.asList(orderDto));
        when(productServiceClient.getProductById(anyLong())).thenReturn(productDto);

        assertDoesNotThrow(() -> reportService.generateSalesReport(LocalDate.now(), LocalDate.now()));
    }

    @Test
    @DisplayName("Generate Sales Report Failure")
    void shouldThrowOrderNotFoundExceptionWhenNoOrdersFound() {
        when(orderServiceClient.getOrdersByDate(any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(Collections.emptyList());

        assertThrows(OrderNotFoundException.class, () -> reportService.generateSalesReport(LocalDate.now(), LocalDate.now()));
    }

    @Test
    @DisplayName("Generate Inventory Report Success")
    void shouldGenerateInventoryReportSuccessfully() {
        when(productServiceClient.getAllProducts()).thenReturn(Arrays.asList(productDto));

        assertDoesNotThrow(() -> reportService.generateInventoryReport());
    }

    @Test
    @DisplayName("Generate Inventory Report Failure")
    void shouldThrowProductNotFoundExceptionWhenNoProductsFound() {
        when(productServiceClient.getAllProducts()).thenReturn(Collections.emptyList());

        assertThrows(ProductNotFoundException.class, () -> reportService.generateInventoryReport());
    }

    private OrderDto getMockOrderDto() {
        return new OrderDto(
                1L,
                LocalDate.now(),
                10.0f,
                20.0f,
                30.0f,
                40.0f,
                1L,
                1L
        );
    }

    private ProductDto getMockProductDto() {
        return new ProductDto(1L, "testProduct");
    }

    private ReportItemDto getMockReportItemDto() {
        return new ReportItemDto(
                "testIdentifier",
                100.0f,
                200.0f,
                1,
                1
        );
    }
}
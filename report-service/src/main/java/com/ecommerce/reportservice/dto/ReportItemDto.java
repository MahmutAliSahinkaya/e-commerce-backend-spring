package com.ecommerce.reportservice.dto;

import lombok.Builder;

@Builder
public record ReportItemDto(
        String identifier,
        float grossSales,
        float netSales,
        int ordersCount,
        int productsCount
) {
}

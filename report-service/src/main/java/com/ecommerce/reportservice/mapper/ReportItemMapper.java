package com.ecommerce.reportservice.mapper;

import com.ecommerce.reportservice.dto.ReportItemDto;
import com.ecommerce.reportservice.entity.ReportItem;

import java.util.List;
import java.util.stream.Collectors;

public class ReportItemMapper {
    public static ReportItem mapToEntity(ReportItemDto reportItemDto) {
        ReportItem reportItem = new ReportItem();
        reportItem.setIdentifier(reportItemDto.identifier());
        reportItem.setGrossSales(reportItemDto.grossSales());
        reportItem.setNetSales(reportItemDto.netSales());
        reportItem.setOrdersCount(reportItemDto.ordersCount());
        reportItem.setProductsCount(reportItemDto.productsCount());
        return reportItem;
    }

    public static ReportItemDto mapToDto(ReportItem reportItem) {
        return new ReportItemDto(
                reportItem.getIdentifier(),
                reportItem.getGrossSales(),
                reportItem.getNetSales(),
                reportItem.getOrdersCount(),
                reportItem.getProductsCount()
        );
    }

    public static List<ReportItemDto> convertToReportItemDtoList(List<ReportItem> reportItems) {
        return reportItems.stream()
                .map(ReportItemMapper::mapToDto)
                .collect(Collectors.toList());
    }
}

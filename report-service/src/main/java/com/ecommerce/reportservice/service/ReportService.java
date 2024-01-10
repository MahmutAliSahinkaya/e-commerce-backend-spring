package com.ecommerce.reportservice.service;

import com.ecommerce.reportservice.dto.ReportItemDto;

import java.time.LocalDate;
import java.util.List;

public interface ReportService {

    List<ReportItemDto> generateSalesReport(LocalDate startDate, LocalDate endDate);

    List<ReportItemDto> generateInventoryReport();

}

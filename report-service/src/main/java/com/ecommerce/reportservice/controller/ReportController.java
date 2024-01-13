package com.ecommerce.reportservice.controller;

import com.ecommerce.reportservice.dto.ReportItemDto;
import com.ecommerce.reportservice.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/sales")
    public ResponseEntity<List<ReportItemDto>> getSalesReport(@RequestParam LocalDate startDate,
                                                              @RequestParam LocalDate endDate) {
        List<ReportItemDto> reportItems = reportService.generateSalesReport(startDate, endDate);
        return new ResponseEntity<>(reportItems, HttpStatus.OK);
    }

    @GetMapping("/inventory")
    public ResponseEntity<List<ReportItemDto>> getInventoryReport() {
        List<ReportItemDto> reportItems = reportService.generateInventoryReport();
        return new ResponseEntity<>(reportItems, HttpStatus.OK);
    }

}

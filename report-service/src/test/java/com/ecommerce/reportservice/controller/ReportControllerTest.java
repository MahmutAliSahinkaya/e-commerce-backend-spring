package com.ecommerce.reportservice.controller;

import com.ecommerce.reportservice.dto.ReportItemDto;
import com.ecommerce.reportservice.service.impl.ReportServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReportController.class)
@ContextConfiguration(classes = {ReportController.class})
class ReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReportServiceImpl reportService;

    @Autowired
    private ObjectMapper objectMapper;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Get Sales Report - Should Return Report Items")
    void getSalesReport_ShouldReturnReportItems() throws Exception {
        List<ReportItemDto> reportItems = Arrays.asList(
                getMockReportItemDto(),
                getMockReportItemDto()
        );
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now();

        when(reportService.generateSalesReport(any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(reportItems);

        mockMvc.perform(MockMvcRequestBuilders.get("/reports/sales")
                        .param("startDate", startDate.toString())
                        .param("endDate", endDate.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(reportItems.size()));
    }

    @Test
    @DisplayName("Get Inventory Report - Should Return Report Items")
    void getInventoryReport_ShouldReturnReportItems() throws Exception {
        List<ReportItemDto> reportItems = Arrays.asList(
                getMockReportItemDto(),
                getMockReportItemDto()
        );

        when(reportService.generateInventoryReport()).thenReturn(reportItems);

        mockMvc.perform(MockMvcRequestBuilders.get("/reports/inventory"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(reportItems.size()));
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
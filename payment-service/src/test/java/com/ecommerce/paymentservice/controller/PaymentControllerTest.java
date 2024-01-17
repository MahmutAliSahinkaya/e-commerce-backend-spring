package com.ecommerce.paymentservice.controller;

import com.ecommerce.paymentservice.dto.OrderDto;
import com.ecommerce.paymentservice.dto.PaymentDto;
import com.ecommerce.paymentservice.entity.enums.PaymentStatus;
import com.ecommerce.paymentservice.service.impl.PaymentServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PaymentController.class)
@ContextConfiguration(classes = {PaymentController.class})
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentServiceImpl paymentService;

    @Autowired
    private ObjectMapper objectMapper;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Process Payment - Should create payment when user role is USER")
    void processPayment_ShouldCreatePayment() throws Exception {
        PaymentDto paymentDto = getMockPaymentDto();
        doNothing().when(paymentService).processPayment(any(PaymentDto.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/payments/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paymentDto)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Validate Payment - Should return true when payment is valid and user role is ADMIN")
    void validatePayment_ShouldReturnTrue_WhenPaymentIsValid() throws Exception {
        when(paymentService.validatePayment(anyLong())).thenReturn(true);

        mockMvc.perform(get("/payments/validate/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }

    @Test
    @DisplayName("Validate Payment - Should return false when payment is not valid and user role is ADMIN")
    void validatePayment_ShouldReturnFalse_WhenPaymentIsNotValid() throws Exception {
        when(paymentService.validatePayment(anyLong())).thenReturn(false);

        mockMvc.perform(get("/payments/validate/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(false));
    }

    @Test
    @DisplayName("Get Payment By User Id - Should return payment details when user role is USER")
    void getPaymentByUserId_ShouldReturnPaymentDetails() throws Exception {
        Long userId = 1L;
        PaymentDto paymentDto = getMockPaymentDto();

        when(paymentService.getPaymentByUserId(userId)).thenReturn(paymentDto);

        mockMvc.perform(get("/payments/details/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paymentId").value(paymentDto.paymentId()))
                .andExpect(jsonPath("$.isPayed").value(paymentDto.isPayed()));
    }


    private OrderDto getMockOrderDto() {
        return OrderDto.builder()
                .orderId(1L)
                .orderDate(LocalDateTime.now())
                .orderDescription("Test Order")
                .totalAmount(100.0)
                .build();
    }

    private PaymentDto getMockPaymentDto() {
        return PaymentDto.builder()
                .paymentId(1L)
                .userId(1L)
                .isPayed(true)
                .paymentStatus(PaymentStatus.COMPLETED)
                .orderDto(getMockOrderDto())
                .build();
    }

}
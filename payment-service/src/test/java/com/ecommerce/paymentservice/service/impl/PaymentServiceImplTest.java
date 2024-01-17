package com.ecommerce.paymentservice.service.impl;

import com.ecommerce.paymentservice.dto.OrderDto;
import com.ecommerce.paymentservice.dto.PaymentDto;
import com.ecommerce.paymentservice.dto.PaymentMessageDto;
import com.ecommerce.paymentservice.dto.UserDto;
import com.ecommerce.paymentservice.entity.Payment;
import com.ecommerce.paymentservice.entity.enums.PaymentStatus;
import com.ecommerce.paymentservice.exception.PaymentNotFoundException;
import com.ecommerce.paymentservice.exception.UserNotFoundException;
import com.ecommerce.paymentservice.repository.PaymentRepository;
import com.ecommerce.paymentservice.service.client.UserServiceClient;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cloud.stream.function.StreamBridge;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private UserServiceClient userServiceClient;

    @Mock
    private StreamBridge streamBridge;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    private OrderDto orderDto;
    private PaymentDto paymentDto;
    private UserDto userDto;
    private Payment payment;
    ArgumentCaptor<Payment> paymentCaptor = ArgumentCaptor.forClass(Payment.class);


    @BeforeEach
    void setUp() {
        orderDto = getMockOrderDto();
        paymentDto = getMockPaymentDto();
        userDto = getMockUserDto();
        payment = getMockPayment();
    }


    @Test
    @DisplayName("Process Payment - Success")
    void shouldProcessPaymentSuccessfully() {
        getMockPaymentDto();
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);
        when(paymentRepository.findById(anyLong())).thenReturn(Optional.of(payment));
        when(userServiceClient.getUserById(anyLong())).thenReturn(userDto);
        doReturn(true).when(streamBridge).send(eq("sendCommunication-out-0"), any(PaymentMessageDto.class));

        paymentService.processPayment(paymentDto);

        verify(paymentRepository, times(3)).save(paymentCaptor.capture());
        var savedPayments = paymentCaptor.getAllValues();
        assertEquals(PaymentStatus.IN_PROGRESS, savedPayments.get(0).getPaymentStatus());
        assertEquals(PaymentStatus.COMPLETED, savedPayments.get(1).getPaymentStatus());
        assertTrue(savedPayments.get(1).getIsPayed());
    }


    @Test
    @DisplayName("Process Payment - Failure due to communication error")
    void shouldThrowRuntimeExceptionWhenCommunicationFails() {
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);
        when(paymentRepository.findById(anyLong())).thenReturn(Optional.of(payment));
        when(userServiceClient.getUserById(anyLong())).thenReturn(userDto);
        doReturn(false).when(streamBridge).send(eq("sendCommunication-out-0"), any(PaymentMessageDto.class));

        assertThrows(RuntimeException.class, () -> paymentService.processPayment(paymentDto));
    }


    @Test
    @DisplayName("Process Payment - Failure due to payment save error")
    void shouldThrowRuntimeExceptionWhenPaymentSaveFails() {
        when(paymentRepository.save(any(Payment.class))).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> paymentService.processPayment(paymentDto));
    }

    @Test
    @DisplayName("Process Payment - Failure due to communication update error")
    void shouldThrowResourceNotFoundExceptionWhenCommunicationUpdateFails() {
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);
        when(paymentRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> paymentService.processPayment(paymentDto));
    }


    @Test
    @DisplayName("Process Payment - Failure")
    void shouldThrowExceptionWhenProcessPaymentFails() {
        when(paymentRepository.save(any(Payment.class))).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> paymentService.processPayment(paymentDto));
    }

    @Test
    @DisplayName("Validate Payment - Success")
    void shouldReturnTrueWhenPaymentIsValid() {
        when(paymentRepository.findById(anyLong())).thenReturn(Optional.of(payment));

        boolean isValid = paymentService.validatePayment(1L);

        assertTrue(isValid);
    }

    @Test
    @DisplayName("Validate Payment - Failure")
    void shouldThrowPaymentNotFoundExceptionWhenPaymentDoesNotExist() {
        when(paymentRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(PaymentNotFoundException.class, () -> paymentService.validatePayment(1L));
    }

    @Test
    @DisplayName("Get Payment By User Id - Success")
    void shouldGetPaymentByUserIdSuccessfully() {
        when(userServiceClient.getUserById(anyLong())).thenReturn(userDto);
        when(paymentRepository.findByUserId(anyLong())).thenReturn(Optional.of(payment));

        PaymentDto result = paymentService.getPaymentByUserId(1L);

        assertNotNull(result);
        assertEquals(paymentDto.paymentId(), result.paymentId());
    }

    @Test
    @DisplayName("Get Payment By User Id - Success")
    void shouldGetPaymentByUserIdSuccessfullyWhenUserExists() {
        when(userServiceClient.getUserById(anyLong())).thenReturn(userDto);
        when(paymentRepository.findByUserId(anyLong())).thenReturn(Optional.of(payment));

        PaymentDto result = paymentService.getPaymentByUserId(1L);

        assertNotNull(result);
        assertEquals(paymentDto.paymentId(), result.paymentId());
    }

    @Test
    @DisplayName("Get Payment By User Id - Failure due to non-existent user")
    void shouldThrowUserNotFoundExceptionWhenUserDoesNotExist() {
        when(userServiceClient.getUserById(anyLong())).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> paymentService.getPaymentByUserId(1L));
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

    private UserDto getMockUserDto() {
        return UserDto.builder()
                .userId(1L)
                .firstName("Test")
                .lastName("User")
                .email("test@email.com")
                .imageUrl("http://example.com/image.jpg")
                .phone("1234567890")
                .build();
    }

    private Payment getMockPayment() {
        return Payment.builder()
                .paymentId(1L)
                .orderId(1L)
                .isPayed(true)
                .paymentStatus(PaymentStatus.COMPLETED)
                .build();
    }
}
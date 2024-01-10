package com.ecommerce.paymentservice.service.impl;

import com.ecommerce.paymentservice.dto.OrderDto;
import com.ecommerce.paymentservice.dto.PaymentDto;
import com.ecommerce.paymentservice.dto.PaymentMessageDto;
import com.ecommerce.paymentservice.dto.UserDto;
import com.ecommerce.paymentservice.entity.Payment;
import com.ecommerce.paymentservice.entity.enums.PaymentStatus;
import com.ecommerce.paymentservice.exception.CommunicationFailureException;
import com.ecommerce.paymentservice.exception.PaymentNotFoundException;
import com.ecommerce.paymentservice.exception.UserNotFoundException;
import com.ecommerce.paymentservice.mapper.PaymentMapper;
import com.ecommerce.paymentservice.repository.PaymentRepository;
import com.ecommerce.paymentservice.service.PaymentService;
import com.ecommerce.paymentservice.service.client.UserServiceClient;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    private static final Logger log = LoggerFactory.getLogger(PaymentServiceImpl.class);

    private final PaymentRepository paymentRepository;
    private final UserServiceClient userServiceClient;
    private final StreamBridge streamBridge;

    public PaymentServiceImpl(PaymentRepository paymentRepository,
                              UserServiceClient userServiceClient,
                              StreamBridge streamBridge) {
        this.paymentRepository = paymentRepository;
        this.userServiceClient = userServiceClient;
        this.streamBridge = streamBridge;
    }


    @Override
    public void processPayment(PaymentDto paymentDto) {
        log.info("Processing payment with id: {}", paymentDto.paymentId());
        Payment payment = PaymentMapper.toEntity(paymentDto);
        payment.setPaymentStatus(PaymentStatus.IN_PROGRESS);
        payment = paymentRepository.save(payment);

        payment.setPaymentStatus(PaymentStatus.COMPLETED);
        payment.setIsPayed(true);
        paymentRepository.save(payment);
        log.info("Payment processed with id: {}", paymentDto.paymentId());

        updateCommunicationStatus(paymentDto.paymentId());

        UserDto userDto = userServiceClient.getUserById(paymentDto.userId());
        OrderDto orderDto = paymentDto.orderDto();
        sendCommunication(paymentDto, orderDto, userDto);
    }


    @Override
    public boolean validatePayment(Long paymentId) {
        log.info("Validating payment with id: {}", paymentId);
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found with paymentId: " + paymentId));
        return payment.getPaymentStatus() == PaymentStatus.COMPLETED;
    }


    @Override
    public PaymentDto getPaymentByUserId(Long userId) {
        UserDto userDto = userServiceClient.getUserById(userId);
        if (userDto == null) {
            throw new UserNotFoundException("User not found with userId: " + userId);
        }

        Payment payment = paymentRepository.findByUserId(userId)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found for user with userId: " + userId));

        return PaymentMapper.toDto(payment);
    }

    @Override
    public boolean updateCommunicationStatus(Long paymentId) {
        boolean isUpdated = false;
        if(paymentId !=null ){
            Payment payment = paymentRepository.findById(paymentId).orElseThrow(
                    () -> new ResourceNotFoundException("Payment", "PaymentId: "+ paymentId)
            );
            payment.setCommunicationSw(true);
            paymentRepository.save(payment);
            isUpdated = true;
        }
        return  isUpdated;
    }

    private void sendCommunication(PaymentDto payment, OrderDto order, UserDto user) {
        var paymentMessageDto = new PaymentMessageDto(payment.paymentId(), payment.isPayed(),
                user.email(), user.phone());
        log.info("Sending Communication request for the details: {}", paymentMessageDto);
        var result = streamBridge.send("sendCommunication-out-0", paymentMessageDto);
        log.info("Is the Communication request successfully triggered ? : {}", result);
        if (!result) {
            throw new CommunicationFailureException("Failed to send communication");
        }

    }

}

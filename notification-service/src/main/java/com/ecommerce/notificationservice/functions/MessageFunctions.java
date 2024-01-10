package com.ecommerce.notificationservice.functions;

import com.ecommerce.notificationservice.dto.PaymentMessageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class MessageFunctions {

    private static final Logger log = LoggerFactory.getLogger(MessageFunctions.class);

    @Bean
    public Function<PaymentMessageDto, PaymentMessageDto> email (){
        return paymentMessageDto -> {
            log.info("Sending email with the details: " + paymentMessageDto.toString());
            return paymentMessageDto;
        };
    }

    @Bean
    public Function<PaymentMessageDto, String> sms (){
        return paymentMessageDto -> {
            log.info("Sending sms with the details: " + paymentMessageDto.toString());
            return paymentMessageDto.userPhone();
        };
    }
}

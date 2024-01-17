package com.ecommerce.favouriteservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableFeignClients
@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
        info = @Info(
                title = "Favourite microservice REST API Documentation",
                description = "E-Commerce-App Favourite microservice REST API Documentation",
                version = "v1")
)
public class FavouriteServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FavouriteServiceApplication.class, args);
    }

}

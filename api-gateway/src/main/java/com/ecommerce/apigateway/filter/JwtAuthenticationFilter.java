package com.ecommerce.apigateway.filter;

import com.ecommerce.apigateway.exception.JwtTokenMalformedException;
import com.ecommerce.apigateway.exception.JwtTokenMissingException;
import com.ecommerce.apigateway.util.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Predicate;

@Component
public class JwtAuthenticationFilter implements GatewayFilter {

    private final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtUtils jwtUtils;

    public JwtAuthenticationFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        log.info("JwtAuthenticationFilter | filter is working");

        ServerHttpRequest request = (ServerHttpRequest) exchange.getRequest();

        final List<String> apiEndpoints = List.of("/signup", "/login","/refreshtoken");

        Predicate<ServerHttpRequest> isApiSecured = r -> apiEndpoints.stream()
                .noneMatch(uri -> r.getURI().getPath().contains(uri));

        log.info("JwtAuthenticationFilter | filter | isApiSecured.test(request) : " + isApiSecured.test(request));

        if (isApiSecured.test(request)) {
            if (!request.getHeaders().containsKey("Authorization")) {
                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.UNAUTHORIZED);

                return response.setComplete();
            }

            final String authorization = request.getHeaders().getOrEmpty("Authorization").get(0);
            final String token = authorization.replace("Bearer ", "");

            log.info("JwtAuthenticationFilter | filter | token : " + token);

            try {
                jwtUtils.validateJwtToken(token);
            } catch (ExpiredJwtException e) {
                log.info("JwtAuthenticationFilter | filter | ExpiredJwtException | error : " + e.getMessage());
                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.UNAUTHORIZED);

                return response.setComplete();

            } catch (IllegalArgumentException | JwtTokenMalformedException | JwtTokenMissingException
                     | SignatureException | UnsupportedJwtException e) {
                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.BAD_REQUEST);

                return response.setComplete();
            }

            Claims claims = jwtUtils.getClaims(token);
            exchange.getRequest().mutate().header("username", String.valueOf(claims.get("username"))).build();
        }

        String correlationId = generateCorrelationId();
        exchange.getRequest().mutate().header("correlation-id", correlationId).build();

        return chain.filter(exchange);

    }

    private String generateCorrelationId() {
        return java.util.UUID.randomUUID().toString();
    }
}

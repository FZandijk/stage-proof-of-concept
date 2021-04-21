package com.poc.productservice.communication;

import com.poc.productservice.dto.OrderToCreate;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class ContactOrderService {

    @Autowired
    private RestTemplate template;

    @Autowired
    private CircuitBreakerFactory circuitBreakerFactory;

    public ResponseEntity<String> createOrderNormally(OrderToCreate order) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("customCircuitBreaker");
        String url = "http://order-service/api/order/place-order";

        return circuitBreaker.run(() -> template.postForEntity(url, order, String.class), throwable -> createOrderFallback());
    }

    public ResponseEntity<String> createOrderUnstable(OrderToCreate order) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("customCircuitBreaker");
        String url = "http://order-service/api/order/place-order-slow";

        return circuitBreaker.run(() -> template.postForEntity(url, order, String.class), throwable -> createOrderFallback());
    }

    public ResponseEntity<String> createOrderFallback() {
        return new ResponseEntity<>("fallback", HttpStatus.REQUEST_TIMEOUT);
    }
}

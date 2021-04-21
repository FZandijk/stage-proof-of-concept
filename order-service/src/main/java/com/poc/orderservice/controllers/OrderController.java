package com.poc.orderservice.controllers;

import com.poc.orderservice.entities.ShopOrder;
import com.poc.orderservice.services.ShopOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final ShopOrderService service;

    public OrderController(ShopOrderService service) {
        this.service = service;
    }

    @PostMapping("/place-order")
    public ResponseEntity<String> placeShopOrder(@RequestBody ShopOrder order) {
        service.placeShopOrder(order);
        return new ResponseEntity<>("order placed", HttpStatus.CREATED);
    }

    @PostMapping("/place-order-slow")
    public ResponseEntity<String> placeShopOrderSlow(@RequestBody ShopOrder order) throws InterruptedException {
        Thread.sleep(4000);
        service.placeShopOrder(order);
        return new ResponseEntity<>("order placed", HttpStatus.CREATED);
    }
}

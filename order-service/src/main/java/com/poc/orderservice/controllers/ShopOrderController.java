package com.poc.orderservice.controllers;

import com.poc.orderservice.models.ShopOrder;
import com.poc.orderservice.services.ShopOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/orders")
public class ShopOrderController {

    private final ShopOrderService service;

    public ShopOrderController(ShopOrderService service) {
        this.service = service;
    }

    /**
     * Create a new Order.
     * @param order The Order that needs to be created.
     * @return CREATED or BAD_REQUEST when validation failed.
     */
    @PostMapping("/place-order")
    public ResponseEntity<Void> placeShopOrder(@RequestBody @Valid ShopOrder order) {
        service.placeShopOrder(order);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}

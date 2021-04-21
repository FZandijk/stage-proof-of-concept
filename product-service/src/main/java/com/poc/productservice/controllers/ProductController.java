package com.poc.productservice.controllers;

import com.poc.productservice.dto.OrderToCreate;
import com.poc.productservice.dto.ProductToOrder;
import com.poc.productservice.services.ProductService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RefreshScope
public class ProductController {

    private final ProductService productService;

    @Value("${my.test}")
    private String testString;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/testMessage")
    public String getTestMessage() {
        return testString;
    }

    @PatchMapping("/place-order")
    public ResponseEntity<String> placeOrder(@RequestBody OrderToCreate order) {
        productService.placeOrderNormally(order);
        return new ResponseEntity<>("Order placed successfully.", HttpStatus.OK);
    }

    @PatchMapping("/place-order-unstable")
    public ResponseEntity<String> placeOrderUnstable(@RequestBody OrderToCreate order) {
        productService.placeOrderFailing(order);
        return new ResponseEntity<>("", HttpStatus.OK);
    }
}

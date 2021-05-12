package com.poc.productservice.controllers;

import com.poc.productservice.entities.Product;
import com.poc.productservice.services.ProductService;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RefreshScope
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


//    @Value("${my.test}")
//    private String testString;


//    @GetMapping("/testMessage")
//    public String getTestMessage() {
//        return testString;
//    }

    /**
     * Calls the ProductService to get all the Product entities in the database.
     * @return OK + List of Products.
     */
    @GetMapping()
    public ResponseEntity<List<Product>> getAllProducts() {
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }


//    @PatchMapping("/place-order")
//    public ResponseEntity<String> placeOrder(@RequestBody OrderToCreate order) {
//        productService.placeOrderNormally(order);
//        return new ResponseEntity<>("Order placed successfully.", HttpStatus.OK);
//    }
//
//    @PatchMapping("/place-order-unstable")
//    public ResponseEntity<String> placeOrderUnstable(@RequestBody OrderToCreate order) {
//        productService.placeOrderFailing(order);
//        return new ResponseEntity<>("", HttpStatus.OK);
//    }
}

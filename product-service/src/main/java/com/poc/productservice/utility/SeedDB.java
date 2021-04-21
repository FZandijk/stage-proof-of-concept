package com.poc.productservice.utility;

import com.poc.productservice.entities.Product;
import com.poc.productservice.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SeedDB implements CommandLineRunner {

    private final ProductRepository repository;

    public SeedDB(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        List<Product> productsToAdd = new ArrayList<>();
        productsToAdd.add(new Product("ballpoint pen", 1, 200));
        productsToAdd.add(new Product("headset", 75, 10));
        productsToAdd.add(new Product("phone case", 10, 10));
        productsToAdd.add(new Product("a pair of socks", 8, 20));
        repository.saveAll(productsToAdd);
    }
}

package com.poc.productservice.services;

import com.poc.productservice.communication.ContactOrderService;
import com.poc.productservice.dto.OrderToCreate;
import com.poc.productservice.dto.ProductToOrder;
import com.poc.productservice.entities.Product;
import com.poc.productservice.exceptions.LimitedStockException;
import com.poc.productservice.repositories.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ContactOrderService contactOrderService;

    public ProductService(ProductRepository productRepository, ContactOrderService contactOrderService) {
        this.productRepository = productRepository;
        this.contactOrderService = contactOrderService;
    }

    @Transactional
    public void placeOrderFailing(OrderToCreate order) {
        List<Product> products = checkStockOfProducts(order.getProductsInOrder());
        productRepository.saveAll(products);

        var response = contactOrderService.createOrderUnstable(order);
        if (response.getStatusCode() == HttpStatus.REQUEST_TIMEOUT) {
            //rollback stock updates
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }

    public void placeOrderNormally(OrderToCreate order) {
        List<Product> products = checkStockOfProducts(order.getProductsInOrder());
        productRepository.saveAll(products);

        var response = contactOrderService.createOrderNormally(order);
        if (response.getStatusCode() != HttpStatus.CREATED) {
            throw new RuntimeException();
        }
    }

    private List<Product> checkStockOfProducts(List<ProductToOrder> productsToOrder) {
        List<Product> productsToUpdate = new ArrayList<>();
        for(ProductToOrder productToOrder : productsToOrder) {
            //does productId exist
            Product product = productRepository.findById(productToOrder.getProductId()).orElseThrow(EntityNotFoundException::new);
            //is stock sufficient
            if (product.getStockTotal() - productToOrder.getAmount() < 0) {
                throw new LimitedStockException("Not enough stock.");
            }
            product.setStockTotal(product.getStockTotal() - productToOrder.getAmount());
            productsToUpdate.add(product);
        }
        return productsToUpdate;
    }
}

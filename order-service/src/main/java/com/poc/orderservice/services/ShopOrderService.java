package com.poc.orderservice.services;

import com.poc.orderservice.entities.ShopOrder;
import com.poc.orderservice.repositories.ShopOrderRepository;
import org.springframework.stereotype.Service;

@Service
public class ShopOrderService {

    private final ShopOrderRepository shopOrderRepository;

    public ShopOrderService(ShopOrderRepository shopOrderRepository) {
        this.shopOrderRepository = shopOrderRepository;
    }

    public void placeShopOrder(ShopOrder order) {
        shopOrderRepository.save(order);
    }
}

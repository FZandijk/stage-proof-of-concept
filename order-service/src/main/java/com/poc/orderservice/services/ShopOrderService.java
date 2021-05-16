package com.poc.orderservice.services;

import com.poc.orderservice.models.ShopOrder;
import com.poc.orderservice.repositories.ShopOrderRepository;
import org.springframework.stereotype.Service;

@Service
public class ShopOrderService {

    private final ShopOrderRepository shopOrderRepository;

    public ShopOrderService(ShopOrderRepository shopOrderRepository) {
        this.shopOrderRepository = shopOrderRepository;
    }

    /**
     * Inserts an Order in the database.
     * @param order The order to insert.
     */
    public void placeShopOrder(ShopOrder order) {
        shopOrderRepository.insert(order);
    }
}

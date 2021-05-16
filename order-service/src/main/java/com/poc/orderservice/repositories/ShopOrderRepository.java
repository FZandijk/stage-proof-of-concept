package com.poc.orderservice.repositories;

import com.poc.orderservice.models.ShopOrder;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ShopOrderRepository extends MongoRepository<ShopOrder, UUID> {
}

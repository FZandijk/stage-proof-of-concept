package com.poc.orderservice.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class ShopOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderId;
    private String deliveryAddress;
    @OneToMany(cascade = CascadeType.PERSIST)
    private Set<OrderItem> productsInOrder = new HashSet<>();

    public ShopOrder() {
    }

    public ShopOrder(String fullAddress, Set<OrderItem> items) {
        this.deliveryAddress = fullAddress;
        this.productsInOrder = items;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }
    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public Set<OrderItem> getProductsInOrder() {
        return productsInOrder;
    }
    public void setProductsInOrder(Set<OrderItem> productsInOrder) {
        this.productsInOrder = productsInOrder;
    }

    public long getOrderId() {
        return orderId;
    }
    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }
}

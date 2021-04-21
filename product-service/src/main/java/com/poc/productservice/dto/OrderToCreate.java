package com.poc.productservice.dto;

import java.util.ArrayList;
import java.util.List;

public class OrderToCreate {
    private String deliveryAddress;
    private List<ProductToOrder> productsInOrder = new ArrayList<>();

    public OrderToCreate() {
    }

    public OrderToCreate(String deliveryAddress, List<ProductToOrder> productToOrders) {
        this.deliveryAddress = deliveryAddress;
        this.productsInOrder = productToOrders;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public List<ProductToOrder> getProductsInOrder() {
        return productsInOrder;
    }

    public void setProductsInOrder(List<ProductToOrder> productsInOrder) {
        this.productsInOrder = productsInOrder;
    }
}

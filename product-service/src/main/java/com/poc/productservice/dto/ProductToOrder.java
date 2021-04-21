package com.poc.productservice.dto;

public class ProductToOrder {
    private long productId;
    private int amount;

    public ProductToOrder() {
    }

    public ProductToOrder(long productId, int amount) {
        this.productId = productId;
        this.amount = amount;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}

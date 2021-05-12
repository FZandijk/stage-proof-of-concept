package com.poc.productservice.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.*;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long productId;

    @Size(max = 450, message = "The maximum length of a description is 450 characters.")
    @NotBlank(message = "The description cannot be blank.")
    private String description;

    @Positive(message = "The price cannot be 0 or negative.")
    @NotNull(message = "The price cannot be null.")
    private Double price;

    @PositiveOrZero(message = "The amount in stock cannot be negative.")
    @NotNull(message = "The amount in stock cannot be null.")
    private Integer amountInStock;

    public Product() {
    }

    public Product(String description, double price, int amountInStock) {
        this.description = description;
        this.price = price;
        this.amountInStock = amountInStock;
    }

    public long getProductId() {
        return productId;
    }
    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getAmountInStock() {
        return amountInStock;
    }
    public void setAmountInStock(Integer amountInStock) {
        this.amountInStock = amountInStock;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Product)) {
            return false;
        }
        Product p = (Product) obj;
        return p.getProductId() == productId && p.getPrice().equals(price) && p.getDescription().equals(description) && p.getAmountInStock().equals(amountInStock);
    }
}

package com.poc.productservice.exceptions;

public class LimitedStockException extends RuntimeException {
    public LimitedStockException(String message) {
        super(message);
    }
}

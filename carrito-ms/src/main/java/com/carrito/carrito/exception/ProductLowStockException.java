package com.carrito.carrito.exception;

public class ProductLowStockException extends RuntimeException {
    public ProductLowStockException(String message) {
        super(message);
    }
}
package com.carrito.carrito.exception;

public class OrdenNotFoundException extends RuntimeException {
    public OrdenNotFoundException(String message) {
        super(message);
    }
}
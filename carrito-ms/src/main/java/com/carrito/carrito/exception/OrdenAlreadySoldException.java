package com.carrito.carrito.exception;

public class OrdenAlreadySoldException extends RuntimeException {
    public OrdenAlreadySoldException(String message) {
        super(message);
    }
}
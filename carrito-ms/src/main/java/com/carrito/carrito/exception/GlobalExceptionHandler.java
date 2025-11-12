package com.carrito.carrito.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(OrdenNotFoundException.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleCarritoItemNotFound(OrdenNotFoundException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("error", "Not Found");
        error.put("mensaje", ex.getMessage());
       /* error.put("codigo", HttpStatus.NOT_FOUND.value());*/
        error.put("timestamp", LocalDateTime.now().toString());

        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(error));
    }

    @ExceptionHandler(ProductLowStockException.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleLowStockItemNotFound(ProductLowStockException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("error", "Products low stock");
        error.put("mensaje", ex.getMessage());
    /*    error.put("codigo", HttpStatus.INTERNAL_SERVER_ERROR.value());*/
        error.put("timestamp", LocalDateTime.now().toString());

        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(error));
    }

    @ExceptionHandler(OrdenAlreadySoldException.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleLowStockItemNotFound(OrdenAlreadySoldException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("error", "Orden Already Sold");
        error.put("mensaje", ex.getMessage());
    /*    error.put("codigo", HttpStatus.NO_CONTENT.value());*/
        error.put("timestamp", LocalDateTime.now().toString());

        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(error));
    }

    @ExceptionHandler(ValidacionException.class)
    public Mono<ResponseEntity<Map<String, Object>>> ValidacionException(ValidacionException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("error", "Campo vacio");
        error.put("mensaje", ex.getMessage());
        /*    error.put("codigo", HttpStatus.NO_CONTENT.value());*/
        error.put("timestamp", LocalDateTime.now().toString());

        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(error));
    }
}


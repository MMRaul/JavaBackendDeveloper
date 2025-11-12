package com.carrito.carrito.infrastructure.repository;

import com.carrito.carrito.infrastructure.entity.Carrito;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface CarritoRepositorio extends ReactiveCrudRepository<Carrito, Integer> {
    Mono<Void> findByIdCarrito(Integer carritoId);
}

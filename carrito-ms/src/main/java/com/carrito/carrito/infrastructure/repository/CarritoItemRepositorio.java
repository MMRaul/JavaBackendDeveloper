package com.carrito.carrito.infrastructure.repository;

import com.carrito.carrito.infrastructure.entity.CarritoItem;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface CarritoItemRepositorio extends ReactiveCrudRepository<CarritoItem, Integer> {
    Flux<CarritoItem> findByIdCarrito(Integer IdCarritoItem);
}

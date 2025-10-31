package com.enyoi.inventario.infrastructure.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import com.enyoi.inventario.infrastructure.entity.ProductoEntity;
import reactor.core.publisher.Mono;

public interface ProductoRepository extends ReactiveCrudRepository<ProductoEntity, String> {
    Mono<Boolean> existsByNombre(String nombre);
}
package com.enyoi.inventario.infrastructure.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import com.enyoi.inventario.infrastructure.entity.InventarioEntity;
import reactor.core.publisher.Mono;

public interface InventarioRepository extends ReactiveCrudRepository<InventarioEntity, String> {
    Mono<InventarioEntity> findByProductoId(String productoId);
}
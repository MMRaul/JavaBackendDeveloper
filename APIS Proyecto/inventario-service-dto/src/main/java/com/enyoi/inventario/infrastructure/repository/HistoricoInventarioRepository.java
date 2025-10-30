package com.enyoi.inventario.infrastructure.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import com.enyoi.inventario.infrastructure.entity.HistoricoInventarioEntity;

public interface HistoricoInventarioRepository extends ReactiveCrudRepository<HistoricoInventarioEntity, String> {
}
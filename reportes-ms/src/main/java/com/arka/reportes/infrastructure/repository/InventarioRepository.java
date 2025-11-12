package com.arka.reportes.infrastructure.repository;

import com.arka.reportes.infrastructure.model.InventarioView;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventarioRepository extends ReactiveCrudRepository<InventarioView, Integer> {

}

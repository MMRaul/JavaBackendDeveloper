package com.arka.reportes.infrastructure.repository;

import com.arka.reportes.infrastructure.model.OrdenesView;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdenesRepository extends ReactiveCrudRepository<OrdenesView, Integer> {
}

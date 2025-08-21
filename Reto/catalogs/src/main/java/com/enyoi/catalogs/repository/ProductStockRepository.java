package com.enyoi.catalogs.repository;

import com.enyoi.catalogs.entity.ProductStock;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface ProductStockRepository extends R2dbcRepository<ProductStock, String> {

    Flux<ProductStock> findByStockGreaterThanEqual(Integer stock);
}

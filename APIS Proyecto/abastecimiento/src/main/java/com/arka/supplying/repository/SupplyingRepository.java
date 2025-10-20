package com.arka.supplying.repository;

import com.arka.supplying.entity.Supplying;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface SupplyingRepository extends R2dbcRepository<Supplying, Long> {
}

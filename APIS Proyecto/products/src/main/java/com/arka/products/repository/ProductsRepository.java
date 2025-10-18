package com.arka.products.repository;

import com.arka.products.models.Products;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface ProductsRepository extends R2dbcRepository<Products, Long>{
}

package com.arka.clients.repository;

import com.arka.clients.model.Clients;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface ClientsRepository extends R2dbcRepository<Clients, Long> {
}

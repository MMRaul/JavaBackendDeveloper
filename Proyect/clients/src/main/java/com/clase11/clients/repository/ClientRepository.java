package com.clase11.clients.repository;

import com.clase11.clients.model.Client;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClientRepository extends MongoRepository<Client, String> {
    Client findByEmail(String email);
}

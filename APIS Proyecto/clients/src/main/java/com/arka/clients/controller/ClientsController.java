package com.arka.clients.controller;

import com.arka.clients.model.Clients;
import com.arka.clients.service.ClientsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/api/v1/clients", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ClientsController {

    private final ClientsService clientsService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Clients> createdClient(@RequestBody Clients clients){
        return clientsService.saveClient(clients);
    }

    @GetMapping
    public Flux<Clients> getAllClient(){
        return clientsService.getAllClients();
    }

    @GetMapping("/{id}")
    public Mono<Clients> getByIdClient(@PathVariable Long id){
        return clientsService.getByIdClient(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Clients> updateClient(@PathVariable Long id, @RequestBody Clients clients){
        return clientsService.updateClient(id,clients);
    }
}

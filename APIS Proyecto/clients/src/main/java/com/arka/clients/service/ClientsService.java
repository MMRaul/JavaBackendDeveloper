package com.arka.clients.service;

import com.arka.clients.model.Clients;
import com.arka.clients.repository.ClientsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.result.method.annotation.ResponseBodyResultHandler;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ClientsService {

    private final ClientsRepository clientsRepository;

    public Flux<Clients> getAllClients(){
        return clientsRepository.findAll()
                .switchIfEmpty(Mono.error(
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "No hay clientes que mostrar")
        ));
    }

    public Mono<Clients> getByIdClient(Long id){
        return clientsRepository.findById(id)
                .switchIfEmpty(Mono.error(
                        new ResponseStatusException(HttpStatus.NOT_FOUND,"Cliente no encontrado")
                ));
    }

    public Mono<Clients> saveClient(Clients clients){

        if (clients.getEstado() == null) {
            clients.setEstado(true);
        }
        if (clients.getNombres() == null || clients.getNombres().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El campo nombres es obligatorio");
        }
        if (clients.getApellidos() == null || clients.getApellidos().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El campo apellidos es obligatorio");
        }
        if (clients.getCorreo() == null || clients.getCorreo().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El campo correo es obligatorio");
        }
        if (clients.getPais() == null || clients.getPais().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El campo pais es obligatorio");
        }
        if (clients.getProvincia() == null || clients.getProvincia().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El campo provincia es obligatorio");
        }
        if (clients.getDireccion() == null || clients.getDireccion().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El campo direccion es obligatorio");
        }

        return clientsRepository.save(clients);
    }

    public Mono<Clients> updateClient(Long id, Clients clientUpdate){
        return clientsRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Cliente no entontrado con ese ID: "+id)))
                .flatMap(clients -> {
                    clients.setNombres(clientUpdate.getNombres());
                    clients.setApellidos(clientUpdate.getApellidos());
                    clients.setCorreo(clientUpdate.getCorreo());
                    clients.setPais(clientUpdate.getPais());
                    clients.setProvincia(clientUpdate.getProvincia());
                    clients.setDireccion(clientUpdate.getDireccion());
                    clients.setTelefono(clientUpdate.getTelefono());
                    clients.setEdad(clientUpdate.getEdad());
                    clients.setFechaNacimiento(clientUpdate.getFechaNacimiento());
                    clients.setEstado(clientUpdate.getEstado() != null ? clientUpdate.getEstado():clients.getEstado());
                    return clientsRepository.save(clients);
                });
    }

}

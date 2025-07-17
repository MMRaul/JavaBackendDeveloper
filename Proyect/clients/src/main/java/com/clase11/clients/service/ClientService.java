package com.clase11.clients.service;

import com.clase11.clients.dto.saveNewClientDto;
import com.clase11.clients.model.Client;
import com.clase11.clients.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public Client saveNewClient(saveNewClientDto dto){
        Client client = mapDtoToClient(dto);
        return clientRepository.save(client);
    }

    public Client getClientByEmail(String email){
        return clientRepository.findByEmail(email);
    }

    private Client mapDtoToClient(saveNewClientDto dto){
        Client client = new Client();
        client.setName(dto.getName());
        client.setEmail(dto.getEmail());
        return client;
    }
}

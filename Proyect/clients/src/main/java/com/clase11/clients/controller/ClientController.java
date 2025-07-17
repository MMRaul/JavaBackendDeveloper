package com.clase11.clients.controller;

import com.clase11.clients.dto.saveNewClientDto;
import com.clase11.clients.model.Client;
import com.clase11.clients.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/client")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    public ResponseEntity<Client> saveClient(@RequestBody saveNewClientDto dto){
         Client response = clientService.saveNewClient(dto);
         return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{email}")
    public ResponseEntity<Client> getClientByEmail(@PathVariable("email") String email){
        Client response = clientService.getClientByEmail(email);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}

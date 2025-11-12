package com.carrito.carrito.controller;

import com.carrito.carrito.application.dto.OrdenDTO;
import com.carrito.carrito.application.dto.OrdenItemDTO;
import com.carrito.carrito.application.dto.ListarOrdenes;
import com.carrito.carrito.application.service.OrdenService;
import com.carrito.carrito.exception.OrdenNotFoundException;
import com.carrito.carrito.exception.ValidacionException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/carrito")
@RequiredArgsConstructor
public class OrdenController {

    private final OrdenService ordenService;

    @PostMapping("/GuardarCarrito")
    public Mono<ResponseEntity<String>> crearCarrito(@RequestBody OrdenDTO request){
        if(request.getCorreo() == null || request.getCorreo().isBlank()){
            throw new ValidacionException("El campo correo es obligatorio");
        }
        if(request.getNombreCliente() == null || request.getNombreCliente().isBlank()){
            throw new ValidacionException("El campo nombre cliente es obligatorio");
        }
        if(request.getDireccionEntrega() == null || request.getDireccionEntrega().isBlank()){
            throw new ValidacionException("El campo direccion entrega es obligatorio");
        }
        return ordenService.GuardarCarrito(request)
                .thenReturn(ResponseEntity.ok("Orden guardada exitosamente"));
    }

    @PostMapping("/ActualizarCarrito")
    public Mono<ResponseEntity<String>> actualizarCarrito(@RequestBody OrdenDTO request){
        if(request.getCorreo() == null || request.getCorreo().isBlank()){
            throw new ValidacionException("El campo correo es obligatorio");
        }
        if(request.getNombreCliente() == null || request.getNombreCliente().isBlank()){
            throw new ValidacionException("El campo nombre cliente es obligatorio");
        }
        if(request.getDireccionEntrega() == null || request.getDireccionEntrega().isBlank()){
            throw new ValidacionException("El campo direccion entrega es obligatorio");
        }
        return ordenService.procesarSiExiste(request)
                .thenReturn(ResponseEntity.ok("Orden actualizada exitosamente"));
    }

    @GetMapping("/Ordenes")
    public Flux<ListarOrdenes> listarOrdenes() {
        return ordenService.retornaOrdenes();
    }

    @GetMapping("/DetalleOrden/{idOrden}")
    public Flux<OrdenItemDTO> listarDetalle(@PathVariable int idOrden) {
        return ordenService.retornarDetalleOrden(idOrden);
    }
/*
    @PostMapping("/VenderOrden2/{idOrden}")
    public Mono<Void> AplicaOrden(@PathVariable int idOrden){
        return carritoService.AplicaOrden(idOrden);
    }*/

    @PostMapping("/VenderOrden")
    public Mono<ResponseEntity<String>> venderOrden(@RequestBody OrdenDTO request){
        return ordenService.AplicarOrden(request)
                .thenReturn(ResponseEntity.ok("Orden " + request.getIdOrden() + " procesada exitosamente"));
    }

}
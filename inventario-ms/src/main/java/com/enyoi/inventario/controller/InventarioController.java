package com.enyoi.inventario.controller;

import com.enyoi.inventario.application.dto.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

import com.enyoi.inventario.application.service.InventarioAppService;

@RestController
@RequestMapping("/api")
public class InventarioController {

    private final InventarioAppService service;

    public InventarioController(InventarioAppService service) {
        this.service = service;
    }

    /**
     * Crea un nuevo producto (y su inventario inicial con stock 0)
     */
    @PostMapping(
            value = "/producto",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Mono<ProductoDTO> crearProducto( @RequestBody ProductoInveDTO dto) {
        if (dto.getNombre() == null || dto.getNombre().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El campo nombre es obligatorio");
        }
        if (dto.getDescripcion() == null || dto.getDescripcion().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El campo descripcion es obligatorio");
        }
        if (dto.getPrecio() == null || dto.getPrecio().toString().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El campo precio es obligatorio");
        }
        if (dto.getCategoria() == null || dto.getCategoria().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El campo categoria es obligatorio");
        }

        return service.registrarProducto(dto);

    }

    /**
     * Lista todos los productos registrados
     */
    @GetMapping(value = "/productos", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<ProductoDTO> listarProductos() {
        return service.listarProductos();
    }

    /**
     * Obtiene los inventarios con stock bajo (menor al umbral)
     */
    @GetMapping(value = "/inventario/bajo", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<InventarioDTO> inventarioBajo() {
        return service.listarInventarioBajo();
    }

    /**
     * Obtiene el inventario de un producto por su ID
     */
    @GetMapping(value = "/inventario/{idProducto}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<InventarioDTO> obtenerInventario(@PathVariable int idProducto) {
        return service.obtenerInventarioPorProductoId(idProducto);
    }

    /**
     * Ajusta el stock de un inventario específico
     */
    @PostMapping(value = "/inventario/{idProducto}/ajustar", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<AjusteResponseDTO> ajustarStock(@PathVariable int idProducto, @Valid @RequestBody AjusteInventarioDTO ajusteDto) {
        return service.ajustarStock(idProducto, ajusteDto.getAccion(), ajusteDto.getCantidad(), ajusteDto.getMotivo());
    }

    /**
     * Obtiene todo el invetario
     */
    @GetMapping(value = "/inventario", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<InventarioDTO> obtenerInventario() {
        return service.listarInventario();
    }
}

package com.enyoi.inventario.controller;

import com.enyoi.inventario.application.dto.AjusteInventarioDTO;
import com.enyoi.inventario.application.dto.AjusteResponseDTO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

import com.enyoi.inventario.application.dto.ProductoDTO;
import com.enyoi.inventario.application.dto.InventarioDTO;
import com.enyoi.inventario.application.service.InventarioAppService;

@RestController
@RequestMapping("/api")
public class InventarioController {

    private final InventarioAppService service;

    public InventarioController(InventarioAppService service) {
        this.service = service;
    }

    /**
     * Crear un nuevo producto (y su inventario inicial con stock 0)
     */
    @PostMapping(
            value = "/productos",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Mono<ProductoDTO> crearProducto(@RequestBody ProductoDTO dto) {
        return service.registrarProducto(dto);
    }

    /**
     * Listar todos los productos registrados
     */
    @GetMapping(value = "/productos", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<ProductoDTO> listarProductos() {
        return service.listarProductos();
    }

    /**
     * Obtener los inventarios con stock bajo (menor al umbral)
     */
    @GetMapping(value = "/inventario/bajo", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<InventarioDTO> inventarioBajo() {
        return service.listarInventarioBajo();
    }

    /**
     * Obtener el inventario de un producto por su ID
     */
    @GetMapping(value = "/inventario/{idProducto}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<InventarioDTO> obtenerInventario(@PathVariable String idProducto) {
        return service.obtenerInventarioPorProductoId(idProducto);
    }

    /**
     * Ajustar el stock de un inventario específico
     */
    @PostMapping(value = "/inventario/{id}/ajustar", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<AjusteResponseDTO> ajustarStock(@PathVariable String id, @Valid @RequestBody AjusteInventarioDTO ajusteDto) {
        return service.ajustarStock(id, ajusteDto.getAccion(), ajusteDto.getCantidad(), ajusteDto.getMotivo());
    }
}

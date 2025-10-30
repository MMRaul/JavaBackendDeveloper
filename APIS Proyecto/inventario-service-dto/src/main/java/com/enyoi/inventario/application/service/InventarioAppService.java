package com.enyoi.inventario.application.service;

import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.UUID;

import com.enyoi.inventario.application.dto.*;
import com.enyoi.inventario.application.mapper.InventarioMapper;
import com.enyoi.inventario.infrastructure.entity.*;
import com.enyoi.inventario.infrastructure.repository.*;

@Service
public class InventarioAppService {
    private final ProductoRepository productoRepo;
    private final InventarioRepository inventarioRepo;
    private final HistoricoInventarioRepository historicoRepo;
    private final R2dbcEntityTemplate template;

    public InventarioAppService(R2dbcEntityTemplate template, ProductoRepository productoRepo, InventarioRepository inventarioRepo, HistoricoInventarioRepository historicoRepo) {
        this.productoRepo = productoRepo;
        this.inventarioRepo = inventarioRepo;
        this.historicoRepo = historicoRepo;
        this.template = template;
    }

    public Mono<ProductoDTO> registrarProducto(ProductoDTO dto) {
        ProductoEntity e = InventarioMapper.toEntity(dto);
        e.setId(UUID.randomUUID().toString());

        return productoRepo.existsByNombre(e.getNombre())
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new IllegalArgumentException(
                                "Ya existe un producto registrado con el nombre: " + e.getNombre()
                        ));
                    }

                    return template.insert(ProductoEntity.class)
                            .using(e)
                            .flatMap(saved -> {
                                InventarioEntity inv = new InventarioEntity();
                                inv.setId(UUID.randomUUID().toString());
                                inv.setProductoId(saved.getId());
                                inv.setStockActual(0);
                                inv.setUmbralMinimo(10);
                                inv.setFechaActualizacion(LocalDateTime.now());
                                return template.insert(InventarioEntity.class)
                                        .using(inv)
                                        .thenReturn(InventarioMapper.toDTO(saved));
                            });
                });
    }

    public Mono<AjusteResponseDTO> ajustarStock(String inventarioId, String accion, int cantidad, String motivo) {
        return inventarioRepo.findById(inventarioId)
                .flatMap(inv -> {
                    int stockAnterior = inv.getStockActual();
                    int nuevoStock = stockAnterior;

                    // Lógica de acción
                    if ("COMPRA".equalsIgnoreCase(accion)) {
                        nuevoStock += cantidad;
                    } else if ("VENTA".equalsIgnoreCase(accion)) {
                        nuevoStock -= cantidad;
                        if (nuevoStock < 0) {
                            return Mono.error(new IllegalArgumentException("La cantidad del producto supera el stock actual"));
                        }
                    } else {
                        return Mono.error(new IllegalArgumentException("Acción no válida (solo COMPRA o VENTA)"));
                    }

                    inv.setStockActual(nuevoStock);
                    inv.setFechaActualizacion(LocalDateTime.now());

                    // Guardar cambio en inventario
                    int finalNuevoStock = nuevoStock;
                    return inventarioRepo.save(inv)
                            .flatMap(saved -> {
                                // Guardar en histórico
                                HistoricoInventarioEntity h = new HistoricoInventarioEntity();
                                h.setId(UUID.randomUUID().toString());
                                h.setProductoId(saved.getProductoId());
                                h.setCantidadAnterior(stockAnterior);
                                h.setCantidadNueva(finalNuevoStock);
                                h.setMotivo(motivo + " (" + accion + ")");
                                h.setFechaCambio(LocalDateTime.now());

                                return template.insert(HistoricoInventarioEntity.class)
                                        .using(h)
                                        // 🔹 Recuperar el producto para armar la respuesta
                                        .then(productoRepo.findById(saved.getProductoId())
                                                .map(prod -> new AjusteResponseDTO(
                                                        prod.getId(),
                                                        prod.getNombre(),
                                                        stockAnterior,
                                                        finalNuevoStock,
                                                        motivo,
                                                        accion.toUpperCase(),
                                                        LocalDateTime.now()
                                                ))
                                        );
                            });
                });
    }

    public Flux<InventarioDTO> listarInventarioBajo() {
        return inventarioRepo.findAll().filter(inv -> inv.getStockActual() < inv.getUmbralMinimo()).map(InventarioMapper::toDTO);
    }

    public Flux<ProductoDTO> listarProductos() {
        return productoRepo.findAll().map(InventarioMapper::toDTO);
    }

    public Mono<InventarioDTO> obtenerInventarioPorProductoId(String productoId) {
        return inventarioRepo.findByProductoId(productoId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException(
                        "No se encontró inventario asociado al producto con ID: " + productoId)))
                .map(InventarioMapper::toDTO);
    }
}

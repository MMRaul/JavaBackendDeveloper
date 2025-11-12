package com.enyoi.inventario.application.service;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.reactive.TransactionalOperator;
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
    private final R2dbcEntityTemplate template;
    private final TransactionalOperator txOperator;

    public InventarioAppService(R2dbcEntityTemplate template, ProductoRepository productoRepo, InventarioRepository inventarioRepo, ConnectionFactory connectionFactory) {
        this.productoRepo = productoRepo;
        this.inventarioRepo = inventarioRepo;
        this.template = template;

        ReactiveTransactionManager tm = new R2dbcTransactionManager(connectionFactory);
        this.txOperator = TransactionalOperator.create(tm);
    }

    public Mono<ProductoDTO> registrarProducto(ProductoInveDTO dto) {
        ProductoDTO prod = new ProductoDTO();
        prod.setNombre(dto.getNombre());
        prod.setDescripcion(dto.getDescripcion());
        prod.setPrecio(dto.getPrecio());
        prod.setCategoria(dto.getCategoria());

        ProductoEntity e = InventarioMapper.toEntity(prod);

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
                                inv.setProductoId(saved.getId());
                                inv.setStockActual(dto.getStock());
                                inv.setUmbralMinimo(dto.getUmbralMinimo());
                                inv.setFechaActualizacion(LocalDateTime.now());
                                return template.insert(InventarioEntity.class)
                                        .using(inv)
                                        .thenReturn(InventarioMapper.toDTO(saved));
                            });
                }).as(txOperator::transactional);
    }

    public Mono<AjusteResponseDTO> ajustarStock(int productoId, String accion, int cantidad, String motivo) {
        return inventarioRepo.findByProductoId(productoId)
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
        return inventarioRepo.findAll()
                .filter(inv -> inv.getStockActual() < inv.getUmbralMinimo()).map(InventarioMapper::toDTO)
                .flatMap(inv ->
                        productoRepo.findById(inv.getProductoId())
                                .map(prod -> {
                                    InventarioDTO dto = new InventarioDTO();
                                    dto.setId(inv.getId());
                                    dto.setProductoId(prod.getId());
                                    dto.setStockActual(inv.getStockActual());
                                    dto.setUmbralMinimo(inv.getUmbralMinimo());
                                    dto.setFechaActualizacion(inv.getFechaActualizacion());

                                    // 🔹 Asignamos también los datos del producto
                                    dto.setNombreProducto(prod.getNombre());
                                    dto.setDescripcionProducto(prod.getDescripcion());
                                    dto.setPrecioProducto(prod.getPrecio());
                                    dto.setCategoriaProducto(prod.getCategoria());

                                    return dto;
                                })
                );
    }

    public Flux<ProductoDTO> listarProductos() {
        return productoRepo.findAll().map(InventarioMapper::toDTO);
    }

    public Mono<InventarioDTO> obtenerInventarioPorProductoId(int productoId) {
        return inventarioRepo.findByProductoId(productoId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException(
                        "No se encontró inventario asociado al producto con ID: " + productoId)))
                .flatMap(inv ->
                        productoRepo.findById(productoId)
                                .map(prod -> {
                                    InventarioDTO dto = new InventarioDTO();
                                    dto.setId(inv.getId());
                                    dto.setProductoId(prod.getId());
                                    dto.setStockActual(inv.getStockActual());
                                    dto.setUmbralMinimo(inv.getUmbralMinimo());
                                    dto.setFechaActualizacion(inv.getFechaActualizacion());

                                    // 🔹 Asignamos también los datos del producto
                                    dto.setNombreProducto(prod.getNombre());
                                    dto.setDescripcionProducto(prod.getDescripcion());
                                    dto.setPrecioProducto(prod.getPrecio());
                                    dto.setCategoriaProducto(prod.getCategoria());

                                    return dto;
                                })
                );
    }

    public Flux<InventarioDTO> listarInventario() {
        return inventarioRepo.findAll()
                .flatMap(inv ->
                        productoRepo.findById(inv.getProductoId())
                                .map(prod -> {
                                    InventarioDTO dto = new InventarioDTO();
                                    dto.setId(inv.getId());
                                    dto.setProductoId(prod.getId());
                                    dto.setStockActual(inv.getStockActual());
                                    dto.setUmbralMinimo(inv.getUmbralMinimo());
                                    dto.setFechaActualizacion(inv.getFechaActualizacion());

                                    // 🔹 Asignamos también los datos del producto
                                    dto.setNombreProducto(prod.getNombre());
                                    dto.setDescripcionProducto(prod.getDescripcion());
                                    dto.setPrecioProducto(prod.getPrecio());
                                    dto.setCategoriaProducto(prod.getCategoria());

                                    return dto;
                                })
                );
    }
}

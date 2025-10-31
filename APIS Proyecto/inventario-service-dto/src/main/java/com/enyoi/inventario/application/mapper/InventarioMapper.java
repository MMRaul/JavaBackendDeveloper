package com.enyoi.inventario.application.mapper;

import com.enyoi.inventario.application.dto.*;
import com.enyoi.inventario.infrastructure.entity.*;

import java.time.LocalDateTime;
import java.util.UUID;

public class InventarioMapper {
  public static ProductoEntity toEntity(ProductoDTO dto) {
    ProductoEntity e = new ProductoEntity();
    e.setId(dto.getId() == null ? UUID.randomUUID().toString() : dto.getId());
    e.setNombre(dto.getNombre());
    e.setDescripcion(dto.getDescripcion());
    e.setPrecio(dto.getPrecio());
    e.setCategoria(dto.getCategoria());
    return e;
  }

  public static ProductoDTO toDTO(ProductoEntity e) {
    ProductoDTO d = new ProductoDTO();
    d.setId(e.getId());
    d.setNombre(e.getNombre());
    d.setDescripcion(e.getDescripcion());
    d.setPrecio(e.getPrecio());
    d.setCategoria(e.getCategoria());
    return d;
  }

  public static InventarioEntity toEntity(InventarioDTO dto) {
    InventarioEntity e = new InventarioEntity();
    e.setId(dto.getId() == null ? UUID.randomUUID().toString() : dto.getId());
    e.setProductoId(dto.getProductoId());
    e.setStockActual(dto.getStockActual());
    e.setUmbralMinimo(dto.getUmbralMinimo());
    e.setFechaActualizacion(dto.getFechaActualizacion() == null ? LocalDateTime.now() : dto.getFechaActualizacion());
    return e;
  }

  public static InventarioDTO toDTO(InventarioEntity e) {
    InventarioDTO d = new InventarioDTO();
    d.setId(e.getId());
    d.setProductoId(e.getProductoId());
    d.setStockActual(e.getStockActual());
    d.setUmbralMinimo(e.getUmbralMinimo());
    d.setFechaActualizacion(e.getFechaActualizacion());
    return d;
  }

  public static HistoricoInventarioEntity toEntity(HistoricoInventarioDTO dto) {
    HistoricoInventarioEntity e = new HistoricoInventarioEntity();
    e.setId(dto.getId() == null ? UUID.randomUUID().toString() : dto.getId());
    e.setProductoId(dto.getProductoId());
    e.setCantidadAnterior(dto.getCantidadAnterior());
    e.setCantidadNueva(dto.getCantidadNueva());
    e.setMotivo(dto.getMotivo());
    e.setFechaCambio(dto.getFechaCambio() == null ? LocalDateTime.now() : dto.getFechaCambio());
    return e;
  }

  public static HistoricoInventarioDTO toDTO(HistoricoInventarioEntity e) {
    HistoricoInventarioDTO d = new HistoricoInventarioDTO();
    d.setId(e.getId());
    d.setProductoId(e.getProductoId());
    d.setCantidadAnterior(e.getCantidadAnterior());
    d.setCantidadNueva(e.getCantidadNueva());
    d.setMotivo(e.getMotivo());
    d.setFechaCambio(e.getFechaCambio());
    return d;
  }
}

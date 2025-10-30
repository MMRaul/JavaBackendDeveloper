package com.enyoi.inventario.infrastructure.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("inventario")
public class InventarioEntity {
    @Id
    private String id;
    private String productoId;
    private int stockActual;
    private int umbralMinimo;
    private LocalDateTime fechaActualizacion;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductoId() {
        return productoId;
    }

    public void setProductoId(String p) {
        this.productoId = p;
    }

    public int getStockActual() {
        return stockActual;
    }

    public void setStockActual(int s) {
        this.stockActual = s;
    }

    public int getUmbralMinimo() {
        return umbralMinimo;
    }

    public void setUmbralMinimo(int u) {
        this.umbralMinimo = u;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime f) {
        this.fechaActualizacion = f;
    }
}

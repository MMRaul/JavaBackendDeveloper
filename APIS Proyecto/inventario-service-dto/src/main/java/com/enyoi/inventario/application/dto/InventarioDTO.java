package com.enyoi.inventario.application.dto;

import java.time.LocalDateTime;

public class InventarioDTO {
    private String id;
    private String productoId;
    private int stockActual;
    private int umbralMinimo;
    private LocalDateTime fechaActualizacion;

    public InventarioDTO() {
    }

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

    public java.time.LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(java.time.LocalDateTime f) {
        this.fechaActualizacion = f;
    }
}

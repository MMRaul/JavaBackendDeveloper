package com.enyoi.inventario.application.dto;

import java.time.LocalDateTime;

public class HistoricoInventarioDTO {
    private String id;
    private String productoId;
    private int cantidadAnterior;
    private int cantidadNueva;
    private String motivo;
    private LocalDateTime fechaCambio;

    public HistoricoInventarioDTO() {
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

    public int getCantidadAnterior() {
        return cantidadAnterior;
    }

    public void setCantidadAnterior(int c) {
        this.cantidadAnterior = c;
    }

    public int getCantidadNueva() {
        return cantidadNueva;
    }

    public void setCantidadNueva(int c) {
        this.cantidadNueva = c;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String m) {
        this.motivo = m;
    }

    public java.time.LocalDateTime getFechaCambio() {
        return fechaCambio;
    }

    public void setFechaCambio(java.time.LocalDateTime f) {
        this.fechaCambio = f;
    }
}

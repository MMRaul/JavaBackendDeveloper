package com.enyoi.inventario.infrastructure.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("historico_inventario")
public class HistoricoInventarioEntity {
    @Id
    private String id;
    private String productoId;
    private int cantidadAnterior;
    private int cantidadNueva;
    private String motivo;
    private LocalDateTime fechaCambio;

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

    public LocalDateTime getFechaCambio() {
        return fechaCambio;
    }

    public void setFechaCambio(LocalDateTime f) {
        this.fechaCambio = f;
    }
}

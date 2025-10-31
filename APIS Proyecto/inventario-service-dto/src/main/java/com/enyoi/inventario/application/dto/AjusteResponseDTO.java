package com.enyoi.inventario.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AjusteResponseDTO {
    private String productoId;
    private String nombreProducto;
    private int cantidadAnterior;
    private int cantidadNueva;
    private String motivo;
    private String accion;
    private LocalDateTime fechaCambio;

    public AjusteResponseDTO() {}

    public AjusteResponseDTO(String productoId, String nombreProducto,
                             int cantidadAnterior, int cantidadNueva,
                             String motivo, String accion, LocalDateTime fechaCambio) {
        this.productoId = productoId;
        this.nombreProducto = nombreProducto;
        this.cantidadAnterior = cantidadAnterior;
        this.cantidadNueva = cantidadNueva;
        this.motivo = motivo;
        this.accion = accion;
        this.fechaCambio = fechaCambio;
    }

}


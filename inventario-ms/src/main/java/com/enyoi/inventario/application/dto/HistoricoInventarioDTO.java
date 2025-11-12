package com.enyoi.inventario.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class HistoricoInventarioDTO {
    private int id;
    private int productoId;
    private int cantidadAnterior;
    private int cantidadNueva;
    private String motivo;
    private LocalDateTime fechaCambio;

    public HistoricoInventarioDTO() {
    }

}

package com.enyoi.inventario.infrastructure.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("historico_inventario")
@Getter
@Setter
public class HistoricoInventarioEntity {
    @Id
    private String id;
    private String productoId;
    private int cantidadAnterior;
    private int cantidadNueva;
    private String motivo;
    private LocalDateTime fechaCambio;

}

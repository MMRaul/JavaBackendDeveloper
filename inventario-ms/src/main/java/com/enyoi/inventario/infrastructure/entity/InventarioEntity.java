package com.enyoi.inventario.infrastructure.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("inventario")
@Getter
@Setter
public class InventarioEntity {
    @Id
    private int id;
    private int productoId;
    private int stockActual;
    private int umbralMinimo;
    private LocalDateTime fechaActualizacion;

}

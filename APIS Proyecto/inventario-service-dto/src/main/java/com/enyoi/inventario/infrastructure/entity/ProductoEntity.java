package com.enyoi.inventario.infrastructure.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Table("producto")
@Getter
@Setter
public class ProductoEntity {
    @Id
    private String id;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private String categoria;

}

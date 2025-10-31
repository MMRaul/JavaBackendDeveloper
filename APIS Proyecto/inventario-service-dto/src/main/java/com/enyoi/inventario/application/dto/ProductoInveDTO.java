package com.enyoi.inventario.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductoInveDTO {
    private String id;

    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private String categoria;
    private int Stock;
    private int umbralMinimo;

    public ProductoInveDTO() {
    }
}

package com.arka.supplying.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductDto {

    private Long idProducto;

    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private int stockActual;
    private String categoria;
    private Boolean estado;

}

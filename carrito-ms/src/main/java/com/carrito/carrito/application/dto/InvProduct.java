package com.carrito.carrito.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor

@Setter
@Getter
public class InvProduct {

    private Integer id;
    private Integer productoId;
    private Integer stockActual;
    private Integer umbralMinimo;
    private String fechaActualizacion;
    private String nombreProducto;
    private String descripcionProducto;
    private BigDecimal precioProducto;
    private String categoriaProducto;
}

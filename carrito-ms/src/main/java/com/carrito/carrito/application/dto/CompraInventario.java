package com.carrito.carrito.application.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CompraInventario {

    private String accion;
    private int cantidad;
    private String motivo;
}

package com.carrito.carrito.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter

public class ListarOrdenes {
    private Integer idOrden;
    private String correo;
    private String nombreCliente;
    private String direccionEntrega;
    private String estado;
    private BigDecimal subTotal;
    private LocalDate fechaCreacion;
    private LocalDate fechaVenta;

}

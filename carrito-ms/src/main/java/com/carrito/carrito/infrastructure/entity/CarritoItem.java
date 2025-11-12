package com.carrito.carrito.infrastructure.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Table("detalleorden")
@Setter
@Getter
public class CarritoItem {

    @Id
    @Column("id")
    private Integer idCarritoItem;

    @Column("orden_id")
    private Integer idCarrito;

    @Column("producto_id")
    private Integer idProducto;

    @Column("cantidad")
    private Integer cantidadItem;

    @Column("precio_unitario")
    private BigDecimal precioUnitario;

}

package com.carrito.carrito.infrastructure.entity;


import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Table("orden")
@Setter
@Getter

public class Carrito {

    @Id
    @Column("id")
    private Integer idCarrito;

    @Column("correo")
    private String correo;

    @Column("nombre_cliente")
    private String nombreCliente;

    @Column("direccion_entrega")
    private String direccionEntrega;

    @Column("estado")
    private String estado;

    @Column("subtotal")
    private BigDecimal subtotal;

    @Column("fecha_creacion")
    private LocalDate fechaCreacion;

    @Column("fecha_venta")
    private LocalDate fechaVenta;

 /*   public Integer getIdCarrito() {
        return idCarrito;
    }

    public void setIdCarrito(Integer idCarrito) {
        this.idCarrito = idCarrito;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }*/
}

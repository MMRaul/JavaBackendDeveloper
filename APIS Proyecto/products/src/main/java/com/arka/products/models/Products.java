package com.arka.products.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Table("productos")
@Getter
@Setter
public class Products {

    @Id
    private Long idProducto;

    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private int stockActual;
    private String categoria;
    private Boolean estado;

}

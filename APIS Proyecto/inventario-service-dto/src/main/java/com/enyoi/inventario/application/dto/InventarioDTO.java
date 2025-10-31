package com.enyoi.inventario.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class InventarioDTO {
    private String id;
    private String productoId;
    private int stockActual;
    private int umbralMinimo;
    private LocalDateTime fechaActualizacion;

    // 🔹 Nuevos campos del producto
    private String nombreProducto;
    private String descripcionProducto;
    private BigDecimal precioProducto;
    private String categoriaProducto;

    public InventarioDTO() {
    }

}

package com.carrito.carrito.application.dto;

import java.math.BigDecimal;

public class OrdenItemDTO {
    private Integer idDetalleOrden;
    private Integer idOrdenC;
    private Integer idProducto;
    private Integer cantidadItems;
    private BigDecimal precioUnitario;

    public Integer getIdDetalleOrden() {
        return idDetalleOrden;
    }

    public void setIdDetalleOrden(Integer idDetalleOrden) {
        this.idDetalleOrden = idDetalleOrden;
    }

    public Integer getIdOrdenC() {
        return idOrdenC;
    }

    public void setIdOrdenC(Integer idOrdenC) {
        this.idOrdenC = idOrdenC;
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public Integer getCantidadItems() {
        return cantidadItems;
    }

    public void setCantidadItems(Integer cantidadItems) {
        this.cantidadItems = cantidadItems;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

}
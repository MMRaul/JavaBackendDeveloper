package com.enyoi.inventario.application.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class AjusteInventarioDTO {

    @NotBlank(message = "La acción es obligatoria (COMPRA o VENTA)")
    @Pattern(regexp = "COMPRA|VENTA", message = "La acción debe ser COMPRA o VENTA")
    private String accion;

    @Min(value = 1, message = "La cantidad debe ser mayor a cero")
    private int cantidad;

    @NotBlank(message = "El motivo no puede estar vacío")
    private String motivo;

    public AjusteInventarioDTO() {}

    public AjusteInventarioDTO(String accion, int cantidad, String motivo) {
        this.accion = accion;
        this.cantidad = cantidad;
        this.motivo = motivo;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}

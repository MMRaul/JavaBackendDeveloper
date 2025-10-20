package com.arka.supplying.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Table("abastecimiento")
@Getter
@Setter
public class Supplying {

    @Id
    private Long idAbastecimiento;

    private Long idProducto;
    private int cantidad;
    private LocalDate fechaRecepcion;
    private String proveedor;
    private BigDecimal costoUnitario;

}

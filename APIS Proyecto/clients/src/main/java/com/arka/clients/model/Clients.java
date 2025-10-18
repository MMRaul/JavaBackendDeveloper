package com.arka.clients.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Table("clientes")
@Getter
@Setter
public class Clients {

    @Id
    private Long id;

    private String nombres;
    private String apellidos;
    private int edad;
    private LocalDate fechaNacimiento;
    private String pais;
    private String provincia;
    private String direccion;
    private String telefono;
    private String correo;
    private Boolean estado;
}

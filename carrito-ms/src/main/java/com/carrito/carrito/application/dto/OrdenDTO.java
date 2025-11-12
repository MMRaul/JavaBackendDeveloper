package com.carrito.carrito.application.dto;

import java.util.List;

    public class OrdenDTO {
        private Integer idOrden;
        private String correo;
        private String nombreCliente;
        private String direccionEntrega;
     /*   private String estado;*/
        private List<OrdenItemDTO> items;

        public List<OrdenItemDTO> getItems() {
            return items;
        }

        public void setItems(List<OrdenItemDTO> items) {
            this.items = items;
        }
/*
        public String getEstado() {
            return estado;
        }

        public void setEstado(String estado) {
            this.estado = estado;
        }*/

        public Integer getIdOrden() {
            return idOrden;
        }

        public void setIdOrden(Integer idOrden) {
            this.idOrden = idOrden;
        }

        public String getCorreo() { return correo; }

        public void setCorreo(String correo) { this.correo = correo; }

        public String getNombreCliente() { return nombreCliente; }

        public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }

        public String getDireccionEntrega() { return direccionEntrega; }

        public void setDireccionEntrega(String direccionEntrega) { this.direccionEntrega = direccionEntrega;}
    }

package com.alvarorf.vehiculosmvc.base;

import java.time.LocalDate;

public class ProductoQuimico extends Producto {

    private String precauciones;
    private LocalDate fechaCaducidad;

    public ProductoQuimico() {

    }

    public ProductoQuimico(String codigoBarras, String modelo, String fabricante,  double precio, boolean enStock, boolean descatalogado, String precauciones, LocalDate fechaCaducidad) {
        super(codigoBarras, modelo, fabricante,  precio, enStock, descatalogado);
        this.precauciones = precauciones;
        this.fechaCaducidad = fechaCaducidad;
    }

    public String getPrecauciones() {
        return precauciones;
    }

    public void setPrecauciones(String precauciones) {
        this.precauciones = precauciones;
    }

    public LocalDate getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(LocalDate fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    @Override
    public String toString() {
        return "ProductoQuimico:" + getCodigoBarras() + " " + getFabricante() + " " + getModelo();
    }
}

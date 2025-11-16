package com.alvarorf.vehiculosmvc.base;

public class Modulo extends Producto {

    private String aplicacion;

    public Modulo() {}

    public Modulo(String codigoBarras, String modelo, String fabricante,  double precio, boolean enStock, boolean descatalogado, String aplicacion) {
        super(codigoBarras, modelo, fabricante, precio, enStock, descatalogado);
        this.aplicacion = aplicacion;
    }

    public String getAplicacion() {
        return aplicacion;
    }

    public void setAplicacion(String aplicacion) {
        this.aplicacion = aplicacion;
    }

    @Override
    public String toString() {
        return "Modulo:" + getCodigoBarras() + " " + getFabricante() + " " + getModelo();
    }
}

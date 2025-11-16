package com.alvarorf.vehiculosmvc.base;

public class OtroProducto extends Producto {

    private String notas;

    public OtroProducto() {}

    public OtroProducto(String codigoBarras, String modelo, String fabricante,  double precio, boolean enStock, boolean descatalogado, String notas) {
        super(codigoBarras, modelo, fabricante, precio, enStock, descatalogado);
        this.notas = notas;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    @Override
    public String toString() {
        return "OtroProducto:" + getCodigoBarras() + " " + getFabricante() + " " + getModelo();
    }
}

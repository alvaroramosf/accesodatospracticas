package com.alvarorf.vehiculosmvc.base;

public class Kit extends Producto {

    private int edadMinima;
    private String nivel;

    public Kit() {

    }


    public Kit(String codigoBarras, String modelo,  String fabricante,  double precio, boolean enStock, boolean descatalogado, int edadMinima, String nivel) {
        super(codigoBarras, modelo, fabricante, precio, enStock, descatalogado);
        this.edadMinima = edadMinima;
        this.nivel = nivel;
    }

    public int getEdadMinima() {
        return edadMinima;
    }

    public void setEdadMinima(int edadMinima) {
        this.edadMinima = edadMinima;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    @Override
    public String toString() {
        return "Kit:" + getCodigoBarras() + " " + getFabricante() + " " + getModelo();
    }
}

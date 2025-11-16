package com.alvarorf.vehiculosmvc.base;

public class Componente extends Producto {

    private String valor;
    private String valor2;

    public Componente() {

    }

    public Componente(String codigoBarras, String modelo, String fabricante,  double precio, boolean enStock, boolean descatalogado, String valor, String valor2) {
        super(codigoBarras, modelo, fabricante, precio, enStock, descatalogado);
        this.valor = valor;
        this.valor2 = valor2;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getValor2() {
        return valor2;
    }

    public void setValor2(String valor2) {
        this.valor2 = valor2;
    }

    @Override
    public String toString() {
        return "Componente:" + getCodigoBarras() + " " + getFabricante() + " " + getModelo();
    }
}

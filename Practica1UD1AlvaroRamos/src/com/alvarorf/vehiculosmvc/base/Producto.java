package com.alvarorf.vehiculosmvc.base;

public class Producto {
    private String codigoBarras;
    private String modelo;
    private String fabricante;
    private double precio;
    private boolean enStock;
    private boolean descatalogado;

    public Producto() {
    }

    public Producto(String codigoBarras, String modelo, String fabricante, double precio, boolean enStock, boolean descatalogado) {
        this.codigoBarras = codigoBarras;
        this.modelo = modelo;
        this.fabricante = fabricante;
        this.precio = precio;
        this.enStock = enStock;
        this.descatalogado = descatalogado;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public String getModelo() {
        return modelo;
    }


    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public boolean isEnStock() {
        return enStock;
    }

    public void setEnStock(boolean enStock) {
        this.enStock = enStock;
    }

    public boolean isDescatalogado() {
        return descatalogado;
    }

    public void setDescatalogado(boolean descatalogado) {
        this.descatalogado = descatalogado;
    }
}

package com.alvarorf.vehiculosmvc;

import com.alvarorf.vehiculosmvc.gui.ProductosControlador;
import com.alvarorf.vehiculosmvc.gui.ProductosModelo;
import com.alvarorf.vehiculosmvc.gui.Ventana;

public class Principal {
    public static void main(String[] args) {
        Ventana vista = new Ventana();
        ProductosModelo modelo = new ProductosModelo();
        ProductosControlador controlador = new ProductosControlador(vista,modelo);
    }
}
